var src=document.getElementById("pic").getAttribute("src");
var pic=document.getElementById("pic");
var timer=setInterval(takePlace(src),50);
function takePlace(src){
  var img=new Image();
  img.src=src;
  if(img.width>0||img.height>0){
    pic.style.width=500+"px";
    pic.style.height=(500/img.width)*img.height+"px";
    alert(pic.style.width);
    clearInterval(timer);
  }
}
