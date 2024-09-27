'use strict';
//arrays para guardar cada imagem na sua cor dominante
let red = [];
let orange = [];
let yellow = [];
let green = [];
let blue_green = [];
let blue = [];
let purple = [];
let pink = [];
let white = [];
let grey = [];
let black = [];
let brown = [];
//array para guardar os anteriores
let allColorArrays = [red, orange, yellow, green, blue_green, blue, purple, pink, white, grey, black, brown];

class ISearchEngine {
    
    constructor(dbase) {
        this.allpictures = new Pool(3000);
        this.colors = ["red", "orange", "yellow", "green", "Blue-green", "blue", "purple", "pink", "white", "grey", "black", "brown"];
        this.redColor = [204, 251, 255, 0, 3, 0, 118, 255, 255, 153, 0, 136];
        this.greenColor = [0, 148, 255, 204, 192, 0, 44, 152, 255, 153, 0, 84];
        this.blueColor = [0, 11, 0, 0, 198, 255, 167, 191, 255, 153, 0, 24];
        this.categories = ["beach", "birthday", "face", "indoor", "manmade/artificial", "manmade/manmade","manmade/urban", "marriage", "nature", "no_people", "outdoor", "party", "people", "snow"];
        this.XML_file = dbase;
        this.XML_db = new XML_Database();
        this.LS_db = new LocalStorageXML();
        this.num_Images = 100;
        this.numshownpic = 35;
        this.imgWidth = 190;
        this.imgHeight = 140;
    }

    init(cnv) {

        //se na última categoria do local storage não existirem o número de imagens especificado correr método que constroi a base de dados
        //retirado pois é necessário no caso de for apenas escolhida a cor
        /*if (this.LS_db.readLS_XML("snow").getElementsByTagName("image").length != this.num_Images) {
            //this.databaseProcessing(cnv);
        }*/

        this.databaseProcessing(cnv);
    }

    // method to build the database which is composed by all the pictures organized by the XML_Database file
    // At this initial stage, in order to evaluate the image algorithms, the method only compute one image.
    // However, after the initial stage the method must compute all the images in the XML file
    databaseProcessing (cnv) {
        let h12color = new ColorHistogram(this.redColor, this.greenColor, this.blueColor);
        let colmoments = new ColorMoments();

        //teste com apenas uma imagem
        /*let img = new Picture(0, 0, 50, 50,"Images/manmade/artificial/img_24.jpg", "test");

        let eventname = "processed_picture_" + img.impath;
        let eventP = new Event(eventname);
        let self = this;
        document.addEventListener(eventname, function(){
            self.imageProcessed(img, eventname);
            console.log(img.hist);
            let max = Math.max(...img.hist);
            
            let index = img.hist.indexOf(max);
            //console.log("hello " + this.colors[index] );
        },false);

        img.computation(cnv, h12color, colmoments, eventP);*/
        

        
        //Percorrer cada categoria para o numero de imagens especificado e fazer a computção de cada imagem
        let load = this.XML_db.loadXMLfile(this.XML_file);        
            for (let i = 0; i < this.categories.length; i++) {                    
            let x = load.getElementsByClassName(this.categories[i]);           
            for (let a = 0; a < this.num_Images; a++) {  
                //Percorrer a informação de cada imagem           
                for (let b = 0; b < x[a].childNodes.length; b++) {
                    let imagePath = x[a].childNodes[b].textContent;
                    //se o nome for == a path prosseguir
                    if (x[a].childNodes[b].nodeName == "path") {
                    let img = new Picture(0, 0, 50, 50,imagePath, "test");
                    let eventname = "processed_picture_" + img.impath;
                    let eventP = new Event(eventname);
                    let self = this;
                    
                    document.addEventListener(eventname, function(){
                        self.imageProcessed(img);
                    },false);
                    img.computation(cnv, h12color, colmoments, eventP);
                }
                    
                }
            }
        }
        
    }

    //When the event "processed_picture_" is enabled this method is called to check if all the images are
    //already processed. When all the images are processed, a database organized in XML is saved in the localStorage
    //to answer the queries related to Color and Image Example
    imageProcessed (img) {
        //inserir imagem no array com todas as imagens
        this.allpictures.insert(img);
        //testes
        /*console.log("image processed " + this.allpictures.stuff.length + eventname);
        console.log(img.hist);
        console.log(img.impath);*/
        if (this.allpictures.stuff.length === (this.num_Images * this.categories.length)) {
            this.createXMLColordatabaseLS();
            //this.createXMLIExampledatabaseLS();
        }
        
    }

    //Method to create the XML database in the localStorage for color queries
    createXMLColordatabaseLS() {
        let load = this.XML_db.loadXMLfile(this.XML_file);   
        let colors = ["red", "orange", "yellow", "green", "Blue-green", "blue", "purple", "pink", "white", "grey", "black", "brown"];

        //Percorrer cada categoria para o numero de imagens especificado e ordenar de acordo com a cor e qual tem o maior número de pixels dessa cor
         for (let i = 0; i < this.categories.length; i++) {
            this.emptyColors();         
            let xml = "<images>\n";           
            let x = load.getElementsByClassName(this.categories[i]);           
            for (let a = 0; a < this.num_Images; a++) {  
                //Percorrer a informação de cada imagem           
                for (let b = 0; b < x[a].childNodes.length; b++) {
                    //se o nome for == a path prosseguir               
                    if (x[a].childNodes[b].nodeName == "path") {
                        let imagePath = x[a].childNodes[b].textContent;
                        //Percorrer todas as imagens e se ambos os paths coincidirem 
                        //(Pois ocorria o erro das imagens não estarem a ser guardadas pela ordem que são lidas logo ficavam com informações erradas) 
                        //inserir no array da sua cor
                        for (let l = 0; l < this.allpictures.stuff.length; l++) {
                            if (imagePath == this.allpictures.stuff[l].impath) {
                                this.insertInColorArray(l);   
                            }    
                        }              
                    } 
                    }           
                }
                //Depois percorrer o número de cores ordenar de acordo com o maior número de pixels da cor dominante e guardar no local storage      
                for (let y = 0; y < allColorArrays.length; y++) {
                    this.sortbyColor(y,allColorArrays[y]);
                    for (let z = 0; z < allColorArrays[y].length; z++) {
                        xml += '\t<image class="' + colors[y] + '">';                 
                        xml += "<path>" + allColorArrays[y][z].impath + '</path>';                 
                        xml += "</image>\n"; 
                        } 
                }     
                xml += "</images>";           
                this.LS_db.saveLS_XML(this.categories[i], xml);         
            }
        // this method should be completed by the students

    }

    //esvazia os arrays
    emptyColors()
    {
         red = [];
         orange = [];
         yellow = [];
         green = [];
         blue_green = [];
         blue = [];
         purple = [];
         pink = [];
         white = [];
         grey = [];
         black = [];
         brown = [];
    }

    //insere a imagem com o indice recebido no array da sua cor dominante
    insertInColorArray(l)
    {
        let colors = ["red", "orange", "yellow", "green", "Blue-green", "blue", "purple", "pink", "white", "grey", "black", "brown"];
        //Encontrar o indice com o maior num de pixels
        let max = Math.max(...this.allpictures.stuff[l].hist);
        //Encontrar a cor com o maior num de pixels
        let index = this.allpictures.stuff[l].hist.indexOf(max);

        //console.log(l+" color " + colors[index] );

        //guardar nos arrays
        switch (colors[index]) {
            case 'red':
                red[red.length] = this.allpictures.stuff[l];
                break;
            case 'orange':
                orange[orange.length] = this.allpictures.stuff[l];
                break;
            case 'yellow':
                yellow[yellow.length] = this.allpictures.stuff[l];
                break;
            case 'green':
                green[green.length] = this.allpictures.stuff[l];
                break;
            case 'Blue-green':
                blue_green[blue_green.length] = this.allpictures.stuff[l];
                break;
            case 'blue':
                blue[blue.length] = this.allpictures.stuff[l];
                break;
            case 'purple':
                purple[purple.length] = this.allpictures.stuff[l];
                break;
            case 'pink':
                pink[pink.length] = this.allpictures.stuff[l];
                break;
            case 'white':
                white[white.length] = this.allpictures.stuff[l];
                break;
            case 'grey':
                grey[grey.length] = this.allpictures.stuff[l];
                break;
            case 'black':
                black[black.length] = this.allpictures.stuff[l];
                break;
            case 'brown':
                brown[brown.length] = this.allpictures.stuff[l];
                break;
        
            default:
                break;
        }
        //guardar no array dos arrays
        allColorArrays = [red,orange, yellow, green, blue_green, blue, purple, pink, white, grey, black, brown];
        return allColorArrays;
    }

    //Method to create the XML database in the localStorage for Image Example queries
    createXMLIExampledatabaseLS() {
        let list_images = new Pool(this.allpictures.stuff.length);
        this.zscoreNormalization();


        // this method should be completed by the students

    }

    //A good normalization of the data is very important to look for similar images. This method applies the
    // zscore normalization to the data
    zscoreNormalization() {
        let overall_mean = [];
        let overall_std = [];

        // Inicialization
        for (let i = 0; i < this.allpictures.stuff[0].color_moments.length; i++) {
            overall_mean.push(0);
            overall_std.push(0);
        }

        // Mean computation I
        for (let i = 0; i < this.allpictures.stuff.length; i++) {
            for (let j = 0; j < this.allpictures.stuff[0].color_moments.length; j++) {
                overall_mean[j] += this.allpictures.stuff[i].color_moments[j];
            }
        }

        // Mean computation II
        for (let i = 0; i < this.allpictures.stuff[0].color_moments.length; i++) {
            overall_mean[i] /= this.allpictures.stuff.length;
        }

        // STD computation I
        for (let i = 0; i < this.allpictures.stuff.length; i++) {
            for (let j = 0; j < this.allpictures.stuff[0].color_moments.length; j++) {
                overall_std[j] += Math.pow((this.allpictures.stuff[i].color_moments[j] - overall_mean[j]), 2);
            }
        }

        // STD computation II
        for (let i = 0; i < this.allpictures.stuff[0].color_moments.length; i++) {
            overall_std[i] = Math.sqrt(overall_std[i]/this.allpictures.stuff.length);
        }

        // zscore normalization
        for (let i = 0; i < this.allpictures.stuff.length; i++) {
            for (let j = 0; j < this.allpictures.stuff[0].color_moments.length; j++) {
                this.allpictures.stuff[i].color_moments[j] = (this.allpictures.stuff[i].color_moments[j] - overall_mean[j]) / overall_std[j];
            }
        }
    }



    //funçao chamada quando a procura é efetuada
    //se a cor não for escolhida faz a procura apenas por palavra
    search()
    {
        let category = document.forms['pesquisa']['search'].value;
        let color = document.forms['pesquisa']['color_pick'].value;
        //console.log(color);
        if (color === 'none') {
            this.searchKeywords(category);
        } else {
            this.searchColor(category,color);
        }
        
    }

    //Method to search images based on a selected color
    searchColor(category, color) {
        let colors = ["red", "orange", "yellow", "green", "Blue-green", "blue", "purple", "pink", "white", "grey", "black", "brown"];
        const cnv = document.getElementById("canvas");
        let Images_path = [];
        let arrayColor = [];
        let paths = [];
        //se existir uma categoria selecionada
        if (category != 'none') {
            let x = this.LS_db.readLS_XML(category);
            let xx = x.getElementsByClassName(color);
            //console.log(xx[0].textContent);
            //Percorre a quantidade de imagens com a cor selecionada na categoria selecionada
            for (let i = 0; i < xx.length; i++) {
                //guarda o path  
                Images_path[Images_path.length] = xx[i].textContent;
                //console.log(Images_path);      
            }    
        }
        //se não existir uma categoria selecionada exibe as 30 imagens com o maior num de pixels da cor
        else{
            //Percorre o num de categorias e faz o mesmo processo que em cima
            for (let i = 0; i < this.categories.length; i++) {
                let x = this.LS_db.readLS_XML(this.categories[i]);
                let xx = x.getElementsByClassName(color);
                for (let j = 0; j < xx.length; j++) {  
                    paths[paths.length] = xx[j].textContent;                        
                }   
            }
            //Percorre o numero de paths guardados
            for (let k = 0; k < paths.length; k++) {
                //Percorre todas as imagens
                for (let l = 0; l < this.allpictures.stuff.length; l++) {
                    //Quando encontrar a imagem com o mesmo path guarda essa imagem num array
                    if (paths[k] == this.allpictures.stuff[l].impath) {
                        arrayColor[arrayColor.length] = this.allpictures.stuff[l];
                        //console.log(this.allpictures[l]); 
                    } 
                }   
            }
            //ordena as imagens por maior num de pixels da cor para o menor
            this.sortbyColor(colors.indexOf(color),arrayColor);
            console.log(arrayColor);
            //guarda as 30 com maior num
            for (let m = 0; m < 30; m++) {
                Images_path[Images_path.length] = arrayColor[m].impath;              
            }
        }
        //exibe
        this.gridView(cnv,Images_path);

    }

    //Method to search images based on keywords
    searchKeywords(category) {
        const cnv = document.getElementById("canvas");
        //carrega ficheiro xml com a informaçao das img
        let xmlDoc = this.XML_db.loadXMLfile(this.XML_file);
        //guarda os paths das 30 primeiras
        let Images_path = this.XML_db.SearchXML(category, xmlDoc, 30);
        //Exibe
        this.gridView(cnv,Images_path);
        // this method should be completed by the students
    }

    //Method to search images based on Image similarities
    searchISimilarity(IExample, dist) {

        // this method should be completed by the students

    }

    //Method to compute the Manhattan difference between 2 images which is one way of measure the similarity
    //between images.
    calcManhattanDist(img1, img2){
        let manhattan = 0;

        for(let i=0; i < img1.color_moments.length; i++){
            manhattan += Math.abs(img1.color_moments[i] - img2.color_moments[i]);
        }
        manhattan /= img1.color_moments.length;
        return manhattan;
    }

    //Method to sort images according to the Manhattan distance measure
    sortbyManhattanDist(idxdist,list){

        // this method should be completed by the students
    }

    //Method to sort images according to the number of pixels of a selected color
    sortbyColor (idxColor, list) {
        list.sort(function (a, b) {
            return b.hist[idxColor] - a.hist[idxColor];
        });
    }

    //Method to visualize images in canvas organized in columns and rows
    gridView (canvas,Images_path) {
        const ctx = canvas.getContext("2d");
        //limpa o canvas
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const p = 2; //padding
        //guarda o indice da proxima imagem a ser exibida
        let count = 0;
        let imgWidth = (canvas.width / 5) ;
        let imgHeight = canvas.height/6;
        //até 7 pois vai ser o numero de linhas a fazer na grid
        for (let i = 0; i <= 7; i++) {
            //horizontais
            const x = i*imgWidth;
            ctx.moveTo(x, 0);
            ctx.lineTo(x, canvas.height);
            ctx.stroke();
            
            //verticais
            const y = i*imgHeight;
            ctx.moveTo(0, y);
            ctx.lineTo(canvas.width, y);
            ctx.stroke();
        }
        //5 imagens por linha 6 por coluna
        for (let yCell = 0; yCell < 6; yCell++) {
        for (let xCell = 0; xCell < 5; xCell++) {
            const x = xCell * imgWidth;
            const y = yCell * imgHeight;
            //cria a imagem com a posição e dimensão calculada
            let img = new Picture(x+p, y+p, imgWidth-p*2, imgHeight-p*2,Images_path[count], "test");
            //exibe
            img.draw(canvas);
            //incrementa o indice 
            count++;
            }
        }

    }
}


class Pool {
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

    empty_Pool () {
        while (this.stuff.length > 0) {
            this.remove();
        }
    }
}

