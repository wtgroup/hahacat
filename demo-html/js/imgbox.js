function hideMax(){
    $(".MAX_div").remove();
    $("#Cover_Div").hide();     //遮罩层隐藏起来
}
var _enter=false;
var scrollFunc = function (e) {
    e = e || window.event;


    if(_enter){
        if (e&&e.preventDefault){
            e.preventDefault();
            e.stopPropagation();
        }else{
            e.returnvalue=false;
        }
    }
    var w=parseInt($(".showMax").css("width"));
    var h=parseInt($(".showMax").css("height"));


    if (e.wheelDelta&&_enter) {  //判断浏览器IE，谷歌滑轮事件
        if (e.wheelDelta > 0) { //当滑轮向上滚动时
            $(".showMax").css({"width":w+Number(28)+"px","height":h+Number(50)+"px"});
            $(".close").css("margin-left",(w-125)<125?125:(w-125)+"px");
        }
        if (e.wheelDelta < 0) { //当滑轮向下滚动时
            $(".showMax").css({"width":(w-28)<280?280:(w-28)+"px","height":(h-50)<500?500:(h-50)+"px"});
            $(".close").css("margin-left",(w-155-28)<125?125:(w-155-28)+"px");
        }
    } else if (e.detail&&_enter) {  //Firefox滑轮事件
        if (e.detail> 0) { //当滑轮向下滚动时
            $(".showMax").css({"width":(w-28)<280?280:(w-28)+"px","height":(h-50)<500?500:(h-50)+"px"});
            $(".close").css("margin-left",(w-155-28)<125?125:(w-155-28)+"px");
        }
        if (e.detail< 0) { //当滑轮向上滚动时


            $(".showMax").css({"width":w+Number(28)+"px","height":h+Number(50)+"px"});
            $(".close").css("margin-left",(w-125)<125?125:(w-125)+"px");
        }
    } } //给页面绑定滑轮滚动事件
if (document.addEventListener) {
    //webkit
    document.addEventListener('mousewheel', scrollFunc, false);
    //firefox
    document.addEventListener('DOMMouseScroll', scrollFunc, false);

}else if(window.attachEvent){//IE
    document.attachEvent('onmousewheel',scrollFunc);
}


//显示放大版图片
function showMax(url){
    // TODO 遮罩层应该动态生成, 不要使用者手动写HTML
    $("#Cover_Div").show(); //显示遮罩层
    var Image=function(){
        return document.createElement("img");
    }
    var DIV=function(){
        return document.createElement("div");
    }
    //放大图的容器
    var div=new DIV();
    //右上角关闭按钮
    var close_div=new DIV();
    var close_img=new Image();
    //大图对象
    var img=new Image();

    $(".MAX_div").remove();     //若已有, 则删除
    div.setAttribute("class","MAX_div");
    close_div.setAttribute("class","close");
    close_div.setAttribute("title","点击关闭");


    img.setAttribute("class","showMax");
    img.src=url;
    //关闭图标
    close_img.src="img/delete.png";
    img.onmouseover=function(){
        _enter=true;
    }
    img.onmouseout=function(){
        _enter=false;
    }
    close_div.onclick=function(){
        hideMax();
    }
    close_div.appendChild(close_img);
    div.appendChild(img);
    div.appendChild(close_div);
    document.body.appendChild(div);

    //var _z=9999;
    var close=$(".close");
    $(document).ready(function(){
        var _move=false;//移动标记
        var _x,_y;//鼠标离控件左上角的相对位置
        var wd;//窗口
        $(".showMax").click(function(){
            //alert("click");//点击（松开后触发）
            //this.style.cursor = "default";//鼠标形状
            //this.style.zIndex = 999;
        }).mousedown(function(e){
            _move=true;
            wd=$(this);

            //this.style.cursor = "move";//鼠标形状
            // this.style.zIndex = _z;//窗口层次
            // _z++;
            _x=e.pageX-(isNaN(parseInt(wd.css("left")))?0:parseInt(wd.css("left")));
            _y=e.pageY-(isNaN(parseInt(wd.css("top")))?0:parseInt(wd.css("top")));

            c_x=e.pageX-(isNaN(parseInt(close.css("left")))?0:parseInt(close.css("left")));
            c_y=e.pageY-(isNaN(parseInt(close.css("top")))?0:parseInt(close.css("top")));

            /*  wd.fadeTo(20, 0.25); *///点击后开始拖动并透明显示
            $(document).mousemove(function(e){
                if(_move){
                    var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置
                    var y=e.pageY-_y;

                    var closeX=e.pageX-c_x;
                    var closeY=e.pageY-c_y;
                    wd.css({top:y,left:x});//控件新位置
                    close.css({top:closeY,left:closeX});
                }
            }).mouseup(function(){
                _move=false;
                /* wd.fadeTo("fast", 1); *///松开鼠标后停止移动并恢复成不透明
            });
        });

    });


    //禁止拖动页面图片在新窗口打开
    function imgdragstart(){return false;}
    for(i in document.images)document.images[i].ondragstart=imgdragstart;
}