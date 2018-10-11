var c = document.getElementById("canvas");
var context = c.getContext("2d");




/****绘制自动换行的字符串****/

function drawText(t,x,y,w){

    var chr = t.split("");
    var temp = "";
    var row = [];

    context.font = "20px Arial";
    context.fillStyle = "black";
    context.textBaseline = "middle";

    for(var a = 0; a < chr.length; a++){
        if( context.measureText(temp).width < w ){
            ;
        }
        else{
            row.push(temp);
            temp = "";
        }
        temp += chr[a];
    }

    row.push(temp);

    for(var b = 0; b < row.length; b++){
        context.fillText(row[b],x,y+(b+1)*20);
    }
}


/*function draw(){
    context.font = "20px Arial";
    context.fillStyle = "black";
    context.textBaseline = "middle";
    context.fillText("Hello, World!\nWhat a nice day.",0,10);
}*/

// draw();

//TODO 改成识别html标签, 可以定制不同行文本的样式
//  hello world!\n<p style="font:10px;size:20px;color:red"></p>



drawText("Hello, World!What a nice day.",0,30,110);
