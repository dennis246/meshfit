
var toggleMode = 1;
//var currentTagParam = '';
var rowNum = 0;

(function () {
    var panelComps = document.getElementsByClassName('panel');
    console.log(panelComps);

    for (var i = 0; i < panelComps.length; i++) {

        //var opdivtag = panelComps[i].querySelector('.output');
       
       var opGrptags = panelComps[i].querySelectorAll('.op');
        for (var j = 0; j < opGrptags.length; j++) {
            opGrptags[j].style.cursor = 'pointer';
            opGrptags[j].addEventListener('click', editPart, false);
        }
    }


    //attach button listener

    var updateBtn = document.querySelector('#updateBtn');
    updateBtn.addEventListener('click', updateNote);
    var x = document.querySelector('.panel');
    console.log(x);

})();


function updateNote(){
    console.log('in updateNote', this);

    var postTo = '/App1/notes';
    var x = document.querySelector('.panel');
    console.log("posting:",x);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", postTo, true); 
    //xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(x.outerHTML);
}


function editPart() {

    console.log('in editPart', this);
    var parentClassName = this.parentElement.className;
    var className = this.className;
    var invClassName;

    if (className === "op") {
        invClassName = className.replace("op", "ip");
    } else {
        invClassName = className.replace("ip", "op");
    }

    if (toggleMode === 1) {

        this.style.visibility = 'hidden';
        var xiptag = this.parentElement.querySelector('.'+invClassName);
        xiptag.style.visibility = 'visible';
        xiptag.addEventListener('focusout', editPart);
        xiptag.className = invClassName;

    } else {
        this.style.visibility = 'hidden';
        console.log(this.parentElement.parentElement);
        var xopdivtag = this.parentElement.parentElement.querySelector('.'+invClassName);
        xopdivtag.style.visibility = 'visible';
        var xoptag = xopdivtag.querySelector('p');

        var iptag = this.querySelector(parentClassName === 'header' ? 'input' : 'textarea'); 
       // xoptag.innerHTML = iptag.value; 
       xoptag.innerHTML = parentClassName === 'header' ? iptag.value : iptag.innerHTML; 
    }

    toggleMode *= -1;

}


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

async function readFileAsText(file) {
    return file.text();
}

function uploadContent() {

    const fileInput = document.getElementById("file");
    var filesList = fileInput.files;
    console.log(filesList);

    const fs = readFileAsText(filesList[0]);
    var fsEnc = btoa(fs);
    console.log(fs);
    console.log(fsEnc);

    const request1 = new Request("/App1/notes", {
        method: "POST",
        body: fs,
    });
}

function initiateFileAdd() {
    var postTo = '/App1/fileAdd';
    var x = 'javaCmd::fileAdd';
    console.log(x);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", postTo, true);
    xhr.send(x);
}



