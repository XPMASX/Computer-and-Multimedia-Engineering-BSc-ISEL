function checkedOnClick(op){

    
    var checkboxesList = document.getElementsByClassName("checkoption");
    for (var i = 0; i < checkboxesList.length; i++) {
       checkboxesList.item(i).checked = false; 
    }

    op.checked = true;
    
 }


 function FirstOption() {
    let fop = document.forms["fdpessoais"]["First"].value;
    let sop = document.forms["fdpessoais"]["Second"].value;
    let top = document.forms["fdpessoais"]["Third"].value; 
    if (fop==sop) {
    document.getElementById("s"+sop).checked = false; }
    if (fop==top) {
    document.getElementById("t"+top).checked = false; }
}

function SecondOption() {
    let fop = document.forms["fdpessoais"]["First"].value;
    let sop = document.forms["fdpessoais"]["Second"].value;
    let top = document.forms["fdpessoais"]["Third"].value; 
    if (sop==fop) {
    document.getElementById("f"+fop).checked = false; }
    if (sop==top) {
    document.getElementById("t"+top).checked = false; }
}

function ThirdOption() {
    let fop = document.forms["fdpessoais"]["First"].value;
    let sop = document.forms["fdpessoais"]["Second"].value;
    let top = document.forms["fdpessoais"]["Third"].value; 
    if (top==fop) {
    document.getElementById("f"+fop).checked = false; }
    if (top==sop) {
    document.getElementById("s"+sop).checked = false; }
}


function Write_Text() {
    let x = document.forms["fdpessoais"]["pimg"].value;
    if (x == "Não") {
    document.forms["fdpessoais"]["outros_pimg"].disabled
    =true;
    document.forms["fdpessoais"]["outros_pimg"].value="";
    } else {
    document.forms["fdpessoais"]["outros_pimg"].disabled
    =false;
    }
}

function getLastLsKey(){
    let ti = window.localStorage.length;
    let key = window.localStorage.key(0);;
    for (let i = 0; i < ti; i++) {
        if(key< window.localStorage.key(i)){
            key = window.localStorage.key(i);
        }  
    }
    return key;

}

function getdataForm() {
    let todo_index = window.localStorage.length;
    let parser;
    let xmlDoc;
    console.log(window.localStorage.length);
    for (let i = 0; i < todo_index; i++) {
        let localStorageRow = window.localStorage.getItem(window.localStorage.key(i));
        
        if (window.DOMParser && localStorageRow[0]=="<") {
            console.log(localStorageRow);
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(localStorageRow,"text/html"); 
            console.log(xmlDoc);
            write(xmlDoc,i);
        }

    }
    
    
}

function write(xmlDoc,i){

    let x = xmlDoc.getElementsByTagName("q");
    document.write("<p style=\"color: rgb(204, 24, 24); font-size: xx-large; font-weight: bold;\"> Respostas dadas no Questionário: Utilizador "+(i+1)+"</p>");
    document.write("<p style=\"color: rgb(159, 38, 38); font-size: x-large; font-weight: bold;\"> Caracterização </p>");
    document.write("<p>1 - Idade: " + x[0].childNodes[0].nodeValue + "</p>");
    document.write("<p>2 - Género: " + x[1].childNodes[0].nodeValue + "</p>");
    if (x[2].childNodes[0].nodeValue != ".") {
        document.write("<p>3 - Frequência de uso de internet: " + x[2].childNodes[0].nodeValue + "</p>");
    }
    document.write("<p>4 - Browsers favoritos: 1º " + x[3].childNodes[0].nodeValue + "</p>" );
    if (x[4].childNodes[0].nodeValue != ".") {
        document.write("<p>4 - Browsers favoritos: 2º " + x[4].childNodes[0].nodeValue + "</p>" );
    }
    if (x[5].childNodes[0].nodeValue != ".") {
        document.write("<p>4 - Browsers favoritos: 3º " + x[5].childNodes[0].nodeValue + "</p>" );
    }
    if (x[6].childNodes[0].nodeValue != ".") {
        document.write("<p>5 - Conhece outros sites de pesquisa de imagem? " + x[6].childNodes[0].nodeValue + "</p>");
    }
    if (x[6].childNodes[0].nodeValue == "Sim") {
        document.write("<p>5 - Quais? " + x[7].childNodes[0].nodeValue + "</p>");
    }
    document.write("<br>");
    document.write("<p style=\"color: rgb(159, 38, 38); font-size: x-large; font-weight: bold;\"> Tarefas </p> ");
    if (x[8].childNodes[0].nodeValue != ".") {
        document.write("<p>6 - Explorar: " + x[8].childNodes[0].nodeValue + "</p>");
    }
    if (x[9].childNodes[0].nodeValue != ".") {
    document.write("<p>7 - Pesquisa Categorias: " + x[9].childNodes[0].nodeValue + "</p>");
    }
    if (x[10].childNodes[0].nodeValue != ".") {
        document.write("<p>8 - Pesquisa Cor: " + x[10].childNodes[0].nodeValue + "</p>");
    }
    if (x[11].childNodes[0].nodeValue != ".") {
        document.write("<p>9 - Pesquisa Imagens Semelhantes: " + x[11].childNodes[0].nodeValue + "</p>");
    }
    if (x[12].childNodes[0].nodeValue != ".") {
        document.write("<p>10 - Qual o tipo de pesquisa que acha mais adequado: " + x[12].childNodes[0].nodeValue + "</p>");
    }
    document.write(" <br>");
    document.write("<p style=\"color: rgb(159, 38, 38); font-size: x-large; font-weight: bold;\"> Avaliação Global </p> <p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">11 - Para cada par de adjetivos, selecione a opção que melhor descreve a sua experiência de utilização deste site.</p>")
    document.write("<p>11.1 - Horrível: " + x[13].childNodes[0].nodeValue + "</p>");
    document.write("<p>11.2 - Aborrecida: " + x[14].childNodes[0].nodeValue + "</p>");
    document.write("<p>11.3 - Frustrante: " + x[15].childNodes[0].nodeValue + "</p>");
    document.write("<p>11.4 - Difícil: " + x[16].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">12 - Como classifica o resultado obtido pela pesquisa por Categoria ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>12.1 - Excelente: " + x[17].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">13 - Como classifica o resultado obtido pela pesquisa por Cor ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>13.1 - Excelente: " + x[18].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">14 - Como classifica o resultado obtido pela pesquisa por Imagens Semelhantes ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>14.1 - Excelente: " + x[19].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">15 - Como classifica a utilidade da pesquisa por Imagens Semelhantes ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>15.1 - Excelente: " + x[20].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">16 - Como classifica o sistema de navegação do site ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>16.1 - Excelente: " + x[21].childNodes[0].nodeValue + "</p>");
    document.write("<p style=\"font-size: x-large; font-weight: bold;\">17 - Como classifica o design do site ( 1 significa \"Mau\" e 5 significa \"Excelente\")?</p>")
    document.write("<p>17.1 - Excelente: " + x[22].childNodes[0].nodeValue + "</p>");
    document.write("<br>");

}

function col_graph()
{
    let todo_index = window.localStorage.length;
    let parser;
    let xmlDoc;
    var dados = [0,0,0,0,0];

    for (let i = 0; i < todo_index; i++) {
        let localStorageRow = window.localStorage.getItem(window.localStorage.key(i));
        
        if (window.DOMParser && localStorageRow[0]=="<") {
            console.log(localStorageRow);
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(localStorageRow,"text/html"); 
            let x = xmlDoc.getElementsByTagName("q");
            switch (x[22].childNodes[0].nodeValue) {
                
                case '1':
                    dados[0]++;
                    break;
                
                case '2':
                    dados[1]++;
                    break;

                case '3':
                    dados[2]++;
                    break;

                case '4':
                    dados[3]++;
                    break;

                case '5':
                    dados[4]++;
                    break;
            
                default:
                    break;
            }
        }

    }

    var chart = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        
        title:{
            text: "Respostas Pergunta 17"
        },
        axisY: {
            title: "Nº de Respostas"
              
        },

        data: [{        
            type: "column",  
            showInLegend: true, 
            legendMarkerColor: "grey",
        
            legendText: "Como classifica o design	do site (1 significa “Mau” e 5 significa “Excelente”)?",
            dataPoints: [      
                { y: dados[0],  label: "1" },
                { y: dados[1],  label: "2" },
                { y: dados[2],  label: "3" },
                { y: dados[3],  label: "4" },
                { y: dados[4], label: "5" },
            ]
        }]
        
    });
    chart.render();
    
}

function pie_graph()
{
    let todo_index = window.localStorage.length;
    let parser;
    let xmlDoc;
    var dados = [0,0,0,0,0];

    for (let i = 0; i < todo_index; i++) {
        let localStorageRow = window.localStorage.getItem(window.localStorage.key(i));
        
        if (window.DOMParser && localStorageRow[0]=="<") {
            console.log(localStorageRow);
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(localStorageRow,"text/html"); 
            let x = xmlDoc.getElementsByTagName("q");
            switch (x[0].childNodes[0].nodeValue) {
                
                case '<18':
                    dados[0]++;
                    break;
                
                case '18-25':
                    dados[1]++;
                    break;

                case '26-33':
                    dados[2]++;
                    break;

                case '34-40':
                    dados[3]++;
                    break;

                case '>40':
                    dados[4]++;
                    break;
            
                default:
                    break;
            }
        }

    }

    var chart = new CanvasJS.Chart("pieContainer", {
        exportEnabled: true,
	    animationEnabled: true,
	    title:{
		    text: "Respostas Pergunta 1"
	    },
	   
        data: [{
            type: "pie",
            showInLegend: true,
            toolTipContent: "{name}: <strong>{y}%</strong>",
            indexLabel: "{name} - {y}%",
            dataPoints: [
                {y: (dados[0]/todo_index)*100, name: "<18"},
                {y: (dados[1]/todo_index)*100, name: "18-25"},
                {y: (dados[2]/todo_index)*100, name: "26-33"},
                {y: (dados[3]/todo_index)*100, name: "34-40"},
                {y: (dados[4]/todo_index)*100, name: ">40"},
            ]
        }]
    });
    chart.render();
    
}

function validateForm_1() {
    
    let d = new Date();
    let nomes = ["idade","sexo","freq","First","Second","Third","pimg","outros_pimg"];
    let xmlRowString = "<Questionario>";
    
    for (let i = 0; i < nomes.length; i++) {
        let Rq= document.forms["fdpessoais"][nomes[i]].value;
        console.log(Rq);
        if (Rq == '') {
            Rq = ".";
        }
        xmlRowString += '<q id="q' + i + '">' + Rq + '</q>';
          
    }
    localStorage.setItem(d.getTime(), xmlRowString);
    console.log(xmlRowString);
    
}

function debug(){
    let data = getLastLsKey(); // continua do ultimo elemento do localStorage
    let xm = window.localStorage.getItem(data);
    console.log(xm);
    console.log(window.localStorage.length);
    for (let i = 0; i < window.localStorage.length; i++) {
        console.log(window.localStorage.getItem(window.localStorage.key(i)));
    }
}

function validateForm_2() {
    let data = getLastLsKey(); // continua do ultimo elemento do localStorage
    let xml = window.localStorage.getItem(data);
    for(let i = 1; i <= 5; i++) {
        let id_str = "t" + (i);
        let Rq= document.forms["ftarefas"][id_str].value;
        if (Rq == '') {
            Rq = ".";
        }

        xml += '<q id="q' + (i+7) + '">' + Rq + '</q>';
    }
    console.log(xml);
    localStorage.setItem(data, xml);
}

function validateForm_3() {
    let data = getLastLsKey(); // continua do ultimo elemento do localStorage
    let xml = window.localStorage.getItem(data);
    let nomes = ["horrivel","aborrecida","frustrante","dificil","mau2","mau3","mau4","mau5","mau6","mau7"];
    
    for (let i = 0; i < nomes.length; i++) {
        let Rq= document.forms["favaliacao"][nomes[i]].value;
        xml += '<q id="q' + (i+14) + '">' + Rq + '</q>';
          
    }
    xml += '</Questionario>';
    console.log(xml);
    localStorage.setItem(data, xml);
}



