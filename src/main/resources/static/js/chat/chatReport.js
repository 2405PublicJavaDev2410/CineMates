function report(a,b){
    let popupW = 500;
    let popupH = 450;
    let left = Math.ceil((window.screen.width - popupW)/2);
    let top = Math.ceil((window.screen.height - popupH)/2);
    myWindow=window.open("/report/report/"+a+"&"+b+"&채팅방","pop","width=500,height=450,left="+left+",top="+top+"");

}