'use strict';

let app = null;

function main() {
    let cnv = document.getElementById('canvas');
    let panel = document.getElementById('img');

    drawImgRect(panel);

    drawCanvasRect(cnv);
    app = new FotoPrint();
    app.init();
    app.drawObj(cnv);
    app.drawObjSel(panel);
    cnv.addEventListener('mousedown', drag, false);
    cnv.addEventListener('dblclick', makenewitem, false);

    
    panel.addEventListener('click', callback, false);
    
}

function drawCanvasRect(cnv) {
    let ctx = cnv.getContext("2d");

    ctx.fillStyle = backColor;
    console.log(backColor);
    ctx.fillRect(0, 0, cnv.width, cnv.height);
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.strokeRect(0, 0, cnv.width, cnv.height);
    
}

function drawImgRect(cnv) {
    let ctx = cnv.getContext("2d");

    ctx.clearRect(0, 0, cnv.width, cnv.height);
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.strokeRect(0, 0, cnv.width, cnv.height);
}

//Drag & Drop operation
//drag
function drag(ev) {
    let mx = null;
    let my = null;
    let cnv = document.getElementById('canvas');

    let xPos = 0;
    let yPos = 0;
    [xPos, yPos] = getMouseCoord(cnv);
    mx = ev.x - xPos;
    my = ev.y - yPos;

    if (app.dragObj(mx, my)) {
        cnv.style.cursor = "pointer";
        cnv.addEventListener('mousemove', move, false);
        cnv.addEventListener('mouseup', drop, false);
    }

}

//Drag & Drop operation
//move
function move(ev) {
    let mx = null;
    let my = null;
    let cnv = document.getElementById('canvas');

    let xPos = 0;
    let yPos = 0;
    [xPos, yPos] = getMouseCoord(cnv);
    mx = ev.x - xPos;
    my = ev.y - yPos;

    app.moveObj(mx, my);
    drawCanvasRect(cnv);
    app.drawObj(cnv);
}

//Drag & Drop operation
//drop
function drop() {
    let cnv = document.getElementById('canvas');

    cnv.removeEventListener('mousemove', move, false);
    cnv.removeEventListener('mouseup', drop, false);
    cnv.style.cursor = "crosshair";
}

//Insert a new Object on Canvas
//dblclick Event
function makenewitem(ev) {
    let mx = null;
    let my = null;
    let cnv = document.getElementById('canvas');

    let xPos = 0;
    let yPos = 0;
    [xPos, yPos] = getMouseCoord(cnv);
    mx = ev.x - xPos;
    my = ev.y - yPos;

    if (app.insertObj(mx, my)) {
        drawCanvasRect(cnv);
        app.drawObj(cnv);
    }
}

//Delete button
//Onclick Event
function remove() {
    let cnv = document.getElementById('canvas');

    app.removeObj();
    drawCanvasRect(cnv);
    app.drawObj(cnv);
}

function setColor()
    {
        color = document.getElementById("color").value;
        console.log(color);
    }

function setBackColor()
{
    let cnv = document.getElementById('canvas');
    backColor = document.getElementById("color").value;
    drawCanvasRect(cnv);
    app.drawObj(cnv);

    

}


function callback(ev)
{
    let mx = null;
    let my = null;
    let cnv = document.getElementById('canvas');
    let panel = document.getElementById('img');

    let xPos = 0;
    let yPos = 0;
    [xPos, yPos] = getMouseCoord(panel);
    mx = ev.x - xPos;
    my = ev.y - yPos;

    if (app.selectObj(mx, my)) {
        drawCanvasRect(cnv);
        app.drawObj(cnv);
    }
}

function insertImage()
{
    app.insertImage();
    drawCanvasRect(document.getElementById('canvas'));
    app.drawObj(document.getElementById('canvas'))
}

//Save button
//Onclick Event
function saveasimage() {
    try {
        let link = document.createElement('a');
        link.download = "imagecanvas.png";
        let canvas = document.getElementById("canvas");
        link.href = canvas.toDataURL("image/png").replace("image/png", "image/octet- stream");
        link.click();
    } catch (err) {
        alert("You need to change browsers OR upload the file to a server.");
    }
}

function getText()
{
    let texto = prompt("Please enter your name");
    app.setText(new Text(50,50, texto, app.getColor()));
    drawCanvasRect(document.getElementById('canvas'));
    app.drawObj(document.getElementById('canvas'))
}


//Mouse Coordinates for all browsers
function getMouseCoord(el) {
    let xPos = 0;
    let yPos = 0;

    while (el) {
        if (el.tagName === "BODY") {
            // deal with browser quirks with body/window/document and page scroll
            let xScroll = el.scrollLeft || document.documentElement.scrollLeft;
            let yScroll = el.scrollTop || document.documentElement.scrollTop;

            xPos += (el.offsetLeft - xScroll + el.clientLeft);
            yPos += (el.offsetTop - yScroll + el.clientTop);
        } else {
            // for all other non-BODY elements
            xPos += (el.offsetLeft - el.scrollLeft + el.clientLeft);
            yPos += (el.offsetTop - el.scrollTop + el.clientTop);
        }

        el = el.offsetParent;
    }
    return [xPos,yPos];
}