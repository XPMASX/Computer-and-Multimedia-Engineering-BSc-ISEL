
let app = null;

function main() {
    
    let canvas = document.querySelector("canvas");
    reSizeCanvas();
    app = new ISearchEngine("xml/My_database.xml");
    app.init(canvas); 
    
       
}

function Generate_Image(canvas) {
    var ctx = canvas.getContext("2d");
    var imgData = ctx.createImageData(0, 0);

    for (var i = 0; i < imgData.data.length; i += 4) {
        imgData.data[i + 0] = 204;
        imgData.data[i + 1] = 0;
        imgData.data[i + 2] = 0;
        imgData.data[i + 3] = 255;
        if ((i >= 8000 && i < 8400) || (i >= 16000 && i < 16400) || (i >= 24000 && i < 24400) || (i >= 32000 && i < 32400))
            imgData.data[i + 1] = 200;
    }
    ctx.putImageData(imgData, 150, 0);
    return imgData;
}

//guarda o canvas como imagem
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

function clearCanvas()
{
    let canvas = document.querySelector("canvas");
    const ctx = canvas.getContext("2d");
    //limpa o canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function reSizeCanvas()
{
    let canvas = document.querySelector("canvas");
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}


