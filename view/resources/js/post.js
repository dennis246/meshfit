function postContent() {

    console.log("inside PostContent fn");
    var postTo = '/App1/notes1';
    var x = document.getElementById('container');
    console.log(x);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", postTo, true);
    //xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(x.outerHTML);
}












/* 
var doc = document.getElementById('gridContainer');


var postLoc = '/App1/grid';

var xhr = new XMLHttpRequest();
xhr.open("POST", postLoc, true);
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.send(JSON.stringify({
    value: value
})); */