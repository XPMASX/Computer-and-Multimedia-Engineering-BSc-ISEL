'use strict';

let color = 'black';
let backColor = '#ffffff';

class FotoPrint
{
    constructor() {
        this.thingInMotion = null;
        this.offsetx = null;
        this.offsety = null;
        this.shpinDrawing = new Pool(100);
        this.panelimg = new Pool(100);
    }

    init() {
        let r = new Rect(100, 100, 75, 75, "red");
        this.shpinDrawing.insert(r);

        let o = new Oval(700, 150, 50, 1, 1, "blue");
        this.shpinDrawing.insert(o);

        let h = new Heart(250, 250, 80, "pink");
        this.shpinDrawing.insert(h);

        let dad = new Picture(200, 200, 70, 70, "imgs/allison1.jpg");
        this.shpinDrawing.insert(dad);

        let bear = new Bear(500, 100, 75, "brown");
        this.shpinDrawing.insert(bear);

        let ghost = new Ghost(500, 300, 'green');
        this.shpinDrawing.insert(ghost);

        let text = new Text(100, 300, "wow", "orange");
        this.shpinDrawing.insert(text);

        let r2 = new Rect(50, 40, 75, 75, "red");
        this.panelimg.insert(r2);

        let o2 = new Oval(200, 75, 50, 1, 1, "blue");
        this.panelimg.insert(o2);

        let h2 = new Heart(340, 50, 100, "pink");
        this.panelimg.insert(h2);

        let dad2 = new Picture(430, 40, 80, 80, "imgs/allison1.jpg");
        this.panelimg.insert(dad2);

        let bear2 = new Bear(600, 83, 75, "brown");
        this.panelimg.insert(bear2);

        let ghost2 = new Ghost(700, 83, 'green');
        this.panelimg.insert(ghost2);

        let car = new Car(850, 70, 'pink');
        this.shpinDrawing.insert(car);

        let car2 = new Car(850, 70, 'pink');
        this.panelimg.insert(car2);

    }

    drawObj(cnv) {
        for (let i = 0; i < this.shpinDrawing.stuff.length; i++) {
            this.shpinDrawing.stuff[i].draw(cnv);
        }
    }

    drawObjSel(cnv) {
        for (let i = 0; i < this.panelimg.stuff.length; i++) {
            this.panelimg.stuff[i].draw(cnv);
        }
    }

    dragObj(mx, my) {
        let endpt = this.shpinDrawing.stuff.length-1;

        for (let i = endpt; i >= 0; i--) {
            if (this.shpinDrawing.stuff[i].mouseOver(mx, my)) {
                this.offsetx = mx - this.shpinDrawing.stuff[i].posx;
                this.offsety = my - this.shpinDrawing.stuff[i].posy;
                let item = this.shpinDrawing.stuff[i];
                this.thingInMotion = this.shpinDrawing.stuff.length - 1;
                this.shpinDrawing.stuff.splice(i, 1);
                this.shpinDrawing.stuff.push(item);
                return true;
            }
        }
        return false;
    }

    moveObj(mx, my) {
        this.shpinDrawing.stuff[this.thingInMotion].setPos(mx - this.offsetx, my - this.offsety);
       
    }

    removeObj () {
        this.shpinDrawing.remove();
    }

    insertObj (mx, my) {
        let item = null;
        let endpt = this.shpinDrawing.stuff.length-1;
        let endpt2 = this.panelimg.stuff.length-1;

        for (let i = endpt; i >= 0; i--) {
            if (this.shpinDrawing.stuff[i].mouseOver(mx,my)) {
                item = this.cloneObj(this.shpinDrawing.stuff[i]);
                this.shpinDrawing.insert(item);
                return true;
            }
        }
        for (let i = endpt2; i >= 0; i--) {
            this.shpinDrawing.insert(this.cloneObjmouse(this.panelimg.stuff[i],mx,my));
            console.log(mx +" / " + my)
            return true;
        }

        return false;
    }

    insertImage()
    {
        let fileInput = document.getElementById('imageUp');
        const fileUrl = window.URL.createObjectURL(fileInput.files[0]);
        this.panelimg.insert(new Picture(600, 200, 140, 140, fileUrl));
    }

    setText(text)
    {
        this.panelimg.insert(new Text(text.posx, text.posy, text.text, text.color,));
    }

    selectObj(mx,my)
    {
        let item = null;
        let endpt = this.panelimg.stuff.length-1;

        for (let i = endpt; i >= 0; i--) {
            if (this.panelimg.stuff[i].mouseOver(mx,my)) {
                item = this.cloneObj(this.panelimg.stuff[i]);
                this.panelimg.insert(item);
                return true;
            }
        }
        return false;
    }

    cloneObj (obj) {
        let item = {};

        switch(obj.name) {
            case "R":
                item = new Rect(obj.posx + 20, obj.posy + 20, obj.w, obj.h, color);
                break;

            case "P":
                item = new Picture(obj.posx + 20, obj.posy + 20, obj.w, obj.h, obj.impath);
                break;

            case "O":
                item = new Oval(obj.posx + 20, obj.posy + 20, obj.r, obj.hor, obj.ver, color);
                break;

            case "H":
                item = new Heart(obj.posx + 20, obj.posy + 20, obj.drx * 4, color);
                break;

            case "B":
                item = new Bear(obj.posx + 20, obj.posy + 20, obj.radius, color);
                break;

            case "T":
                item = new Text(obj.posx + 20, obj.posy + 20, obj.text, color);
                break;

            case "G":
                item = new Ghost(obj.posx + 20, obj.posy + 20, color);
                break;

            case "C":
                item = new Car(obj.posx + 20, obj.posy + 20, color);
                break;
            
            default: throw new TypeError("Can not clone this type of object");
        }
        return item;
    }
    
    cloneObjmouse (obj,mx,my) {
        let item = {};

        switch(obj.name) {
            case "R":
                item = new Rect(mx, my, obj.w, obj.h, color);
                break;

            case "P":
                item = new Picture(mx, my, obj.w, obj.h, obj.impath);
                break;

            case "O":
                item = new Oval(mx, my, obj.r, obj.hor, obj.ver, color);
                break;

            case "H":
                item = new Heart(mx, my, obj.drx * 4, color);
                break;

            case "B":
                item = new Bear(mx, my, obj.radius, color);
                break;

            case "T":
                item = new Text(mx, my, obj.text, color);
                break;

            case "G":
                item = new Ghost(mx, my, color);
                break;

            case "C":
                item = new Car(mx, my, color);
                break;
            
            default: throw new TypeError("Can not clone this type of object");
        }
        return item;
    }

    getColor()
    {
        return(color);
    }

    
}


class Pool
{
    constructor (maxSize) {
        this.size = maxSize;
        this.stuff = [];

    }

    insert (obj) {
        if (this.stuff.length < this.size) {
            this.stuff.push(obj);
        } else {
            alert("The application is full: there isn't more memory space to include objects");
        }
    }

    remove () {
        if (this.stuff.length !== 0) {
            this.stuff.pop();
        } else {
           alert("There aren't objects in the application to delete");
        }
    }
}

