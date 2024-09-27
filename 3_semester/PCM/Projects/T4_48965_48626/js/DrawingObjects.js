class DrawingObjects
{
    constructor (px, py, name) {
        if (this.constructor === DrawingObjects) {
            // Error Type 1. Abstract class can not be constructed.
            throw new TypeError("Can not construct abstract class.");
        }

        //else (called from child)
        // Check if all instance methods are implemented.
        if (this.draw === DrawingObjects.prototype.draw) {
            // Error Type 4. Child has not implemented this abstract method.
            throw new TypeError("Please implement abstract method draw.");
        }

        if (this.mouseOver === DrawingObjects.prototype.mouseOver) {
            // Error Type 4. Child has not implemented this abstract method.
            throw new TypeError("Please implement abstract method mouseOver.");
        }

        this.posx = px;
        this.posy = py;
        this.name = name;
    }

    draw (cnv) {
        // Error Type 6. The child has implemented this method but also called `super.foo()`.
        throw new TypeError("Do not call abstract method draw from child.");
    }

    mouseOver(mx, my) {
        // Error Type 6. The child has implemented this method but also called `super.foo()`.
        throw new TypeError("Do not call abstract method mouseOver from child.");
    }


    sqDist(px1, py1, px2, py2) {
        let xd = px1 - px2;
        let yd = py1 - py2;

        return ((xd * xd) + (yd * yd));
    }

    setPos (x,y) {
        this.posx = x;
        this.posy = y;
    }
}

class Rect extends DrawingObjects
{

    constructor (px, py, w, h, c) {
        super(px, py, 'R');
        this.w = w;
        this.h = h;
        this.color = c;
    }

    draw (cnv) {
        let ctx = cnv.getContext("2d");

        ctx.fillStyle = this.color;
        ctx.fillRect(this.posx, this.posy, this.w, this.h);

    }

    mouseOver(mx, my) {
        return ((mx >= this.posx) && (mx <= (this.posx + this.w)) && (my >= this.posy) && (my <= (this.posy + this.h)));

    }
}

class Picture extends DrawingObjects
{

    constructor (px, py, w, h, impath) {
        super(px, py, 'P');
        this.w = w;
        this.h = h;
        this.impath = impath;
        this.imgobj = new Image();
        this.imgobj.src = this.impath;
    }

    draw (cnv) {
        let ctx = cnv.getContext("2d");

        if (this.imgobj.complete) {
            ctx.drawImage(this.imgobj, this.posx, this.posy, this.w, this.h);
            console.log("Debug: N Time");

        } else {
            console.log("Debug: First Time");
            let self = this;
            this.imgobj.addEventListener('load', function () {
                ctx.drawImage(self.imgobj, self.posx, self.posy, self.w, self.h);
            }, false);
        }
    }

    mouseOver(mx, my) {
        return ((mx >= this.posx) && (mx <= (this.posx + this.w)) && (my >= this.posy) && (my <= (this.posy + this.h)));
    }
}

class Oval extends DrawingObjects
{
    constructor (px, py, r, hs, vs, c) {
        super(px, py, 'O');
        this.r = r;
        this.radsq = r * r;
        this.hor = hs;
        this.ver = vs;
        this.color = c;
    }

    mouseOver (mx, my) {
        let x1 = 0;
        let y1 = 0;
        let x2 = (mx - this.posx) / this.hor;
        let y2 = (my - this.posy) / this.ver;

        return (this.sqDist(x1,y1,x2,y2) <= (this.radsq));
    }

    draw (cnv) {
        let ctx = cnv.getContext("2d");

        ctx.save();
        ctx.translate(this.posx,this.posy);
        ctx.scale(this.hor,this.ver);
        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.arc(0, 0, this.r, 0, 2*Math.PI, true);
        ctx.closePath();
        ctx.fill();
        ctx.restore();
    }
}


class Heart extends DrawingObjects
{
    constructor (px, py, w, c) {
        super(px, py, 'H');
        this.h = w * 0.7;
        this.drx = w / 4;
        this.radsq = this.drx * this.drx;
        this.ang = .25 * Math.PI;
        this.color = c;
    }

    outside (x, y, w, h, mx, my) {
        return ((mx < x) || (mx > (x + w)) || (my < y) || (my > (y + h)));
    }

    draw (cnv) {
        let leftctrx = this.posx - this.drx;
        let rightctrx = this.posx + this.drx;
        let cx = rightctrx + this.drx * Math.cos(this.ang);
        let cy = this.posy + this.drx * Math.sin(this.ang);
        let ctx = cnv.getContext("2d");

        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.moveTo(this.posx, this.posy);
        ctx.arc(leftctrx, this.posy, this.drx, 0, Math.PI - this.ang, true);
        ctx.lineTo(this.posx, this.posy + this.h);
        ctx.lineTo(cx,cy);
        ctx.arc(rightctrx, this.posy, this.drx, this.ang, Math.PI, true);
        ctx.closePath();
        ctx.fill();
    }

    mouseOver (mx, my) {
        let leftctrx = this.posx - this.drx;
        let rightctrx = this.posx + this.drx;
        let qx = this.posx - 2 * this.drx;
        let qy = this.posy - this.drx;
        let qwidth = 4 * this.drx;
        let qheight = this.drx + this.h;

        let x2 = this.posx;
        let y2 = this.posy + this.h;
        let m = (this.h) / (2 * this.drx);

        //quick test if it is in bounding rectangle
        if (this.outside(qx, qy, qwidth, qheight, mx, my)) {
            return false;
        }

        //compare to two centers
        if (this.sqDist (mx, my, leftctrx, this.posy) < this.radsq) return true;
        if (this.sqDist(mx, my, rightctrx, this.posy) < this.radsq) return true;

        // if outside of circles AND less than equal to y, return false
        if (my <= this.posy) return false;

        // compare to each slope
        // left side
        if (mx <= this.posx) {
            return (my < (m * (mx - x2) + y2));
        } else {  //right side
            m = -m;
            return (my < (m * (mx - x2) + y2));
        }
    }
}


class Bear extends DrawingObjects {

    constructor(x, y, radius, color) {
  
      super(x, y, 'B');
      this.radius = radius;
      this.color = "#5b0e0e";
      this.contrast_color = "white";
      this.head_color = color;
  
      this.head = new Oval(this.posx, this.posy, this.radius / 1.5, 1, 1, this.head_color);
  
      this.left_outter_ear = new Oval(this.posx - this.radius /2 , this.posy - this.radius / 2, this.radius / 3.2, 1, 1, this.head_color);
      this.right_outter_ear = new Oval(this.posx + this.radius / 2, this.posy - this.radius / 2, this.radius / 3.2, 1, 1, this.head_color);
      this.left_inner_ear = new Oval(this.posx - this.radius / 2, this.posy - this.radius / 2, this.radius / 6, 1, 1, this.color);
      this.right_inner_ear = new Oval(this.posx + this.radius / 2, this.posy - this.radius / 2, this.radius / 6, 1, 1, this.color);
  
      this.left_outter_eye = new Oval(this.posx - this.radius / 4, this.posy - this.radius / 5, this.radius / 10, 1, 1, "black");
      this.right_outter_eye = new Oval(this.posx + this.radius / 4, this.posy - this.radius / 5, this.radius / 10, 1, 1, "black");
      this.left_inner_eye = new Oval(this.posx - this.radius / 4 - 4, this.posy - this.radius / 5 - 4, this.radius / 30, 1, 1, this.contrast_color);
      this.right_inner_eye = new Oval(this.posx + this.radius / 4 - 4, this.posy - this.radius / 5 - 4, this.radius / 30, 1, 1, this.contrast_color);
  
      this.outter_nose = new Oval(this.posx, this.posy, this.radius / 6, 1.2, 1, "black");
      this.inner_nose = new Oval(this.posx - 10, this.posy - 7, this.radius / 30, 1.2, 1, this.contrast_color);
    }
  
    mouseOver(mx, my) {
  
      return (this.head.mouseOver(mx,my) || this.left_outter_ear.mouseOver(mx,my) || this.right_outter_ear.mouseOver(mx,my));
    }

    update() {

        this.head.setPos(this.posx, this.posy);
    
        this.left_outter_ear.setPos(this.posx - this.radius / 2, this.posy - this.radius / 2);
        this.right_outter_ear.setPos(this.posx + this.radius / 2, this.posy - this.radius / 2);
        this.left_inner_ear.setPos(this.posx - this.radius / 2, this.posy - this.radius / 2);
        this.right_inner_ear.setPos(this.posx + this.radius / 2, this.posy - this.radius / 2);
    
        this.left_outter_eye.setPos(this.posx - this.radius / 4, this.posy - this.radius / 5);
        this.right_outter_eye.setPos(this.posx + this.radius / 4, this.posy - this.radius / 5);
    
        this.left_inner_eye.setPos(this.posx - this.radius / 4 - 4, this.posy - this.radius / 5 - 4);
        this.right_inner_eye.setPos(this.posx + this.radius / 4 - 4, this.posy - this.radius / 5 - 4);
    
        this.outter_nose.setPos(this.posx, this.posy);
        this.inner_nose.setPos(this.posx - 10, this.posy - 7);
      }
  
    draw(cnv) {
  
      let ctx = cnv.getContext("2d");
  
      this.update();

      this.left_outter_ear.draw(cnv);
      this.right_outter_ear.draw(cnv);
      this.left_inner_ear.draw(cnv);
      this.right_inner_ear.draw(cnv);
  
      this.head.draw(cnv);
  
      this.left_outter_eye.draw(cnv);
      this.right_outter_eye.draw(cnv);
  
      this.left_inner_eye.draw(cnv);
      this.right_inner_eye.draw(cnv);
  
      this.outter_nose.draw(cnv);
      this.inner_nose.draw(cnv);
  
      ctx.save();
      {
        ctx.translate(this.posx, this.posy + (this.radius / 6) * 1.2);
        ctx.strokeStyle = "black";
        ctx.scale(1.2, 1);
        ctx.beginPath();
        ctx.arc(-10, 0, 10, 0, Math.PI, false);
        ctx.stroke();
        ctx.closePath();
        ctx.beginPath();
        ctx.arc(10, 0, 10, 0, Math.PI, false);
        ctx.stroke();
        ctx.closePath();
      }
      ctx.restore();
    }
  }

  class Ghost extends DrawingObjects {
    constructor(px, py, c) {
        super(px, py, 'G');
        this.color = c;
        let olhoe = new Oval(this.posx, this.posy, 10, 1, 1, "white");
        let olhoee = new Oval(this.posx, this.posy, 3, 1, 1, "black");
        let olhod = new Oval(this.posx, this.posy, 10, 1, 1, "white");
        let olhodd = new Oval(this.posx, this.posy, 3, 1, 1, "black");
        let rect = new Rect(this.posx, this.posy, 100, 100, "black");
        let rect2 = new Rect(this.posx, this.posy, 110, 110, "black");

        this.olhoe = olhoe;
        this.olhoee = olhoee;
        this.olhod = olhod;
        this.olhodd = olhodd;
        this.rect = rect;
        this.rect2 = rect2;
    }

    setPos(x, y) {
        super.setPos(x, y);
        this.olhoe.setPos(this.posx + 20, this.posy - 10);
        this.olhoee.setPos(this.posx + 15, this.posy - 7);
        this.olhod.setPos(this.posx + 55, this.posy - 10);
        this.olhodd.setPos(this.posx + 50, this.posy - 7);
        this.rect.setPos(this.posx - 8, this.posy - 50);
        this.rect2.setPos(this.posx - 13, this.posy - 55);
    }

    mouseOver(mx, my) {

        return this.rect.mouseOver(mx, my) || this.rect2.mouseOver(mx, my);
    }

    draw(cnv) {
        this.setPos(this.posx, this.posy);
        let ctx = cnv.getContext("2d");

        // moldura
        ctx.beginPath();
        this.rect2.draw(cnv);
        this.rect.draw(cnv);


        // cabeça do fantasma retangulo + arco
        ctx.beginPath();
        ctx.fillStyle = this.color;
        ctx.fillRect(this.posx, this.posy, 84, 20);
        ctx.arc(this.posx + 42, this.posy, 42, 0, Math.PI, true);
        ctx.fill();

         // parte debaixo (triangulos)
        ctx.beginPath();
        ctx.moveTo(this.posx , this.posy );
        ctx.lineTo(this.posx, this.posy + 40);
        ctx.lineTo(this.posx, this.posy + 40);
        ctx.lineTo(this.posx + 50, this.posy);
        ctx.fillStyle = this.color;
        ctx.fill();
         // parte debaixo (triangulos)
        ctx.beginPath();
        ctx.moveTo(this.posx, this.posy);
        ctx.lineTo(this.posx + 40, this.posy + 40);
        ctx.lineTo(this.posx + 85, this.posy);
        ctx.fillStyle = this.color;
        ctx.fill();
        // // parte debaixo (triangulos)
        ctx.beginPath();
        ctx.moveTo(this.posx, this.posy);
        ctx.lineTo(this.posx + 84, this.posy + 40);
        ctx.lineTo(this.posx + 84, this.posy + 5);
        ctx.fillStyle = this.color;
        ctx.fill();

        // olhos
        this.olhoe.draw(cnv);

        this.olhoee.draw(cnv);

        this.olhod.draw(cnv);

        this.olhodd.draw(cnv);



    }
}

class Text extends DrawingObjects
{
    constructor(x, y, text, color) 
    {
        super(x, y, 'T');
        this.color = color;
        this.text = text;
    }

    draw(cnv)
    {
        var ctx = cnv.getContext("2d");
        ctx.font = "30px Arial";
        ctx.fillStyle = this.color;
        ctx.fillText(this.text, this.posx, this.posy);
        
    }

    mouseOver(mx, my) {
        let cnv = document.getElementById('canvas');
        var ctx = cnv.getContext("2d");
        let width = ctx.measureText(this.text).width;
        console.log(ctx.measureText(this.text));
        let height = 30;
        return ((mx >= this.posx) && (mx <= (this.posx + width)) && ((my+5) >= (this.posy-15)) && (my <= (this.posy-30 + height)));

    }
}


class Car extends DrawingObjects {
    constructor(px, py, c) {
        super(px, py, 'C');
        this.color = c;
        

        let body = new Rect(this.posx, this.posy, 130, 35, this.color);
        let head = new Rect(this.posx, this.posy, 90, 40, this.color);
        let window = new Rect(this.posx, this.posy, 35, 25, 'lightblue');
        let rodae = new Oval(this.posx, this.posy, 17, 1, 1, "black");
        let rodaee = new Oval(this.posx, this.posy, 10, 1, 1, 'white');
        let rodad = new Oval(this.posx, this.posy, 17, 1, 1, "black");
        let rodadd = new Oval(this.posx, this.posy, 10, 1, 1, "white");

        this.body = body;
        this.head = head;
        this.window = window;
        this.rodae = rodae;
        this.rodaee = rodaee;
        this.rodad = rodad;
        this.rodadd = rodadd;

    }

    setPos(x, y) {
        super.setPos(x, y);
        this.rodae.setPos(this.posx + 30, this.posy + 35);
        this.rodaee.setPos(this.posx + 30, this.posy + 37);
        this.rodad.setPos(this.posx + 100, this.posy + 35);
        this.rodadd.setPos(this.posx + 100, this.posy + 37);
        this.body.setPos(this.posx, this.posy);
        this.head.setPos(this.posx + 20, this.posy - 30);
        this.window.setPos(this.posx + 48, this.posy - 23);
    }

    mouseOver(mx, my) {

        return this.head.mouseOver(mx, my) || this.body.mouseOver(mx, my) || 
        this.rodae.mouseOver(mx, my) || this.rodad.mouseOver(mx, my);
    }

    draw(cnv) {
        this.setPos(this.posx, this.posy);
        let ctx = cnv.getContext("2d");

         // moldura
         

        this.head.draw(cnv);


        
        
        this.window.draw(cnv);
        this.rodae.draw(cnv);
        this.rodaee.draw(cnv);
        this.rodad.draw(cnv);
        this.rodadd.draw(cnv);
        this.body.draw(cnv);
        ctx.beginPath();
        ctx.moveTo(this.posx + 48 +17, this.posy - 24 );
        ctx.lineTo(this.posx + 48 +17, this.posy );
        ctx.stroke();
        ctx.beginPath();
        ctx.moveTo(this.posx + 48, this.posy - 12 );
        ctx.lineTo(this.posx + 48 +35, this.posy - 12 );
        ctx.stroke();





    }
}