class Picture {

    constructor(px, py, w, h, impath, cat) {
        this.posx = px;
        this.posy = py;
        this.w = w;
        this.h = h;
        this.impath = impath;
        this.imgobj = new Image();
        this.imgobj.src = this.impath;
        this.original_w = this.imgobj.width;
        this.original_h = this.imgobj.height;
        this.category = cat;
        this.hist = [];
        this.dcolor;
        this.color_moments = [];
        this.manhattanDist = [];
    }

   

    draw(cnv) {
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

    //method to apply the algorithms to the image.
    //Because the image have to loaded from the server, the same strategy used in the method draw()
    //is used here to access the image pixels. We do not exactly when the image in loaded and computed.
    //For this reason the event "processed_picture" was created to alert the application (ISearchEngine)
    computation(cnv, histcol, colorMom, eventP) {
        let ctx = cnv.getContext("2d");
        

        if (this.imgobj.complete) {
            console.log("Debug: N Time");
            ctx.drawImage(this.imgobj, 0, 0, 100, 100);
            let pixels =  ctx.getImageData(0, 0, 100, 100);
            //let pixels = Generate_Image(cnv);
            this.hist = histcol.count_Pixels(pixels);
            
            //this.build_Color_Rect(cnv, this.hist, histcol.redColor, histcol.greenColor, histcol.blueColor);
            //this.color_moments = colorMom.moments(this.imgobj, cnv);
            ctx.clearRect( 0, 0, cnv.width, cnv.height);
            document.dispatchEvent(eventP);

        } else {
            console.log("Debug: First Time");
            let self = this;
            this.imgobj.addEventListener('load', function () {
                ctx.drawImage(self.imgobj, 0, 0, 100, 100);
                let pixels =  ctx.getImageData(0, 0, 100, 100);
                //let pixels = Generate_Image(cnv);
                self.hist = histcol.count_Pixels(pixels);
                
                //self.build_Color_Rect(cnv, self.hist, histcol.redColor, histcol.greenColor, histcol.blueColor);
                //self.color_moments = colorMom.moments(self.imgobj, cnv);
                ctx.clearRect( 0, 0, cnv.width, cnv.height);
                document.dispatchEvent(eventP);
            }, false);
        }
        
        
        // this method should be completed by the students

    }

    //method used for debug. It shows the color and the correspondent number of pixels obtained by
    //the colorHistogram algorithm
    build_Color_Rect (cnv, hist, redColor, greenColor, blueColor) {
        let ctx = canvas.getContext("2d");
        let text_y = 390;
        let rect_y = 400;
        let hor_space = 80;

        ctx.font = "12px Arial";
        for (let c = 0; c < redColor.length; c++) {
            ctx.fillStyle = "rgb(" + redColor[c] + "," + greenColor[c] + "," + blueColor[c] + ")";
            ctx.fillRect(c * hor_space, rect_y, 50, 50);
            if (c === 8) {
                ctx.fillStyle = "black";
            }
            ctx.fillText(hist[c], c * hor_space, text_y);
        }
    }

    setPosition (px, py) {
        this.posx = px;
        this.posy = py;
    }

    mouseOver(mx, my) {
        if ((mx >= this.posx) && (mx <= (this.posx + this.w)) && (my >= this.posy) && (my <= (this.posy + this.h))) {
            return true;
        }
        return false;
    }

}


//Class to compute the Color Histogram algorithm. It receives the colors and computes the histogram
//through the method count_Pixels()
class ColorHistogram {
    constructor(redColor, greenColor, blueColor) {
        this.redColor = redColor;
        this.greenColor = greenColor;
        this.blueColor = blueColor;
        
        // this method should be completed by the students
    }

    count_Pixels (pixels) {
        let color = [0,0,0,0,0,0,0,0,0,0,0,0];

        //percorre os pixels duma imagem
        for (let i = 0, n = pixels.data.length; i < n; i+=4) { // 4 = h,s,v,alpha (por isso +=4)

            //percorre o num de cores
            for (let j = 0; j < 12; j++){
            
                //modulo da diferença do rgb predefenido e rgb do pixel
                let difr = Math.abs(pixels.data[i+0] - this.redColor[j]);
                let difg = Math.abs(pixels.data[i+1] - this.greenColor[j]);
                let difb = Math.abs(pixels.data[i+2] - this.blueColor[j]);
                let diftotal = difr + difg + difb;//soma das diferenças

                //testes
                /*if (i == 0 && j == 11) {
                    console.log(pixels.data[i+0] + ","+ pixels.data[i+1] + "," + pixels.data[i+2]);
                    console.log(difr);
                    console.log(difg);
                    console.log(difb);
                }*/
                
                //limites standard
                let limiar1 = 160;
                let limiar2 = 105;

                //limites para cada cor
                switch (j) {
                    //red
                    case 0:
                        limiar2 = 110;
                        limiar1 = 160;
                        break;
                    //orange
                    case 1:
                        limiar2 = 105;
                        limiar1 = 150;
                        break;
                    //yellow
                    case 2:
                        limiar2 = 180;
                        limiar1 = 250;
                        break;
                    //green
                    case 3:
                        limiar2 = 160;
                        limiar1 = 220;
                        break;
                    //blue-green
                    case 4:
                        limiar2 = 120;
                        limiar1 = 140;
                        break;
                    //blue
                    case 5:
                        limiar2 = 170;
                        limiar1 = 250;
                        break;
                    //purple
                    case 6:
                        limiar1 = 120;
                        limiar2 = 70;
                        break;
                    //pink
                    case 7:
                        limiar2 = 50;
                        break;
                    //white
                    case 8:
                        limiar1 = 70;
                        limiar2 = 30;
                        break;
                    //grey
                    case 9:
                        limiar1 = 60;
                        limiar2 = 15;
                        break;
                    //black
                    case 10:
                        limiar1 = 40;
                        limiar2 = 15;
                        break;
                    //brown
                    case 11:
                        limiar1 = 50;
                        limiar2 = 30;
                        break;
                
                    default:
                        break;
                }

                //se as condiçoes forem aceites (powerpoint) salta para o próximo pixel
                if (diftotal < limiar1 && difr < limiar2 && difg < limiar2 && difb < limiar2 ) {
                    color[j]++;
                    break;
                }
            }
        }
        
        return color;
        // this method should be completed by the students
    }
}


//Class to compute the Color Moments algorithm. It computes the statistics moments
//through the method moments(). The moments are computed in the HSV color space. The method rgdToHsv is used
//to translate the pixel into the HSV color space
class ColorMoments {
    constructor() {
        this.h_block = 3;
        this.v_block = 3;
    }

    rgbToHsv (rc, gc, bc) {
        let r = rc / 255;
        let g = gc / 255;
        let b = bc / 255;

        let max = Math.max(r, g, b);
        let min = Math.min(r, g, b);
        let h = null, s = null, v = max;

        let dif = max - min;
        s = max == 0 ? 0 : dif / max;

        if (max == min) {
            h = 0;
        } else {
            switch (max) {
                case r:
                    h = (g - b) / dif + (g < b ? 6 : 0);
                    break;
                case g:
                    h = (b - r) / dif + 2;
                    break;
                case b:
                    h = (r - g) / dif + 4;
                    break;
            }
            h /= 6;
        }
        return [h, s, v];
    }

    moments (imgobj, cnv) {
        let wBlock = Math.floor(imgobj.width / this.h_block);
        let hBlock = Math.floor(imgobj.height / this.v_block);
        let n = wBlock * hBlock;
        let descriptor = [];

        let ctx = cnv.getContext("2d");
        ctx.drawImage(imgobj, 0, 0);

        // this method should be completed by the students


    }
}





