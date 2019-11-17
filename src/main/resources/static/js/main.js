'use strict';

var chatPage = document.querySelector('#chat-page');
var homeTitle = document.querySelector('#homeTitle');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');

var stompClient = null;
var username = null;
var homeName = null;
var mineNumber = null;
var homeNumber = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    if(username) {
        //usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    //event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/topic/'+mineNumber, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/chat.connect", {}, {});

    //connectingElement.classList.add('hidden');
    //检查加入的房间
    homeReady();
}


function onError(error) {
    //connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    //connectingElement.style.color = 'red';
}


function sendMessage(event) {
    console.debug("==>send message")
    var messageContent = messageInput.value.trim();
    var sendNumberStr = null == homeNumber?mineNumber:homeNumber;
    if(messageContent && stompClient) {
        var messageNow = messageInput.value.trim();
        console.debug("==>messageNow:"+messageNow);
        var chatMessage = {
            senderTo: sendNumberStr,
            content: rsaEncrypt(messageNow),
            type: 'CHAT'
        };
        stompClient.send("/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function connectListener(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var messageNow = messageInput.value;
        console.debug("==>messageNow:"+messageNow);
        var chatMessage = {
            senderTo: mineNumber,
            content: rsaEncrypt(messageNow),
            type: 'CHAT'
        };

        stompClient.send("/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.content;
        $("h6").text(message.sender);
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.content;
        $("h6").text(message.sender);
    } else {

        console.debug("==>received message:"+message.content)
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(rsaDecrypt(message.content));
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

//usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

//页面加载完毕执行浏览器注册
window.onload=function(){
    console.debug("==>开始连接")

    /*Fingerprint2.get(function (components) {
        //console.log(components) // an array of components: {key: ..., value: ...}
        console.debug("==>gingerprint.length:"+components);
        console.debug("==>gingerprint:"+components);

    })*/
    register("123");
    //var fingerprint = new Fingerprint2().get();
}

/**检查登录房间*/
function homeReady(){
    var urlHome = getQueryVariable("home");
    if(null!=urlHome && urlHome && urlHome.toString().trim().length>0){
        //检查是否已经加入房间
        checkJoinenHomeNumber(urlHome, true);
    }else{
        checkJoinenHomeNumber(null, false);
    }

}

function checkJoinenHomeNumber(newHomeNumber, needUpdateHome){
    $.ajax({
        type:"GET",// get或者post
        url:"/api/account/joinedHome",// 请求的url地址
        contentType:"application/json;charset=utf-8",
        timeout:15000,//3秒后提示错误
        beforeSend:function(){
            // 发送之前就会进入这个函数
            // return false 这个ajax就停止了不会发 如果没有return false 就会继续
        },
        success:function(data){ // 成功拿到结果放到这个函数 data就是拿到的结果
            console.debug("==>data:"+data)
            if(data.code == 200){
                console.debug("==>data:"+ data);
                homeNumber = data.data;
                if(!needUpdateHome){
                    return
                }
                console.debug("==>nowHome:"+ homeNumber);
                console.debug("==>newHomeNumber:"+ newHomeNumber);
                if(null==homeNumber || homeNumber != newHomeNumber){
                    showJoinHomeAlert(newHomeNumber);
                }
            }else if(data.code>1600 && data.code<1699){
                swal(data.data);
            }else {
                swal({text: "请求出错", icon: "error"});
                console.debug("==>"+data.data);
            }
        },
        error:function(){//失败的函数
            swal({text: "请求出错", icon: "error"});
        },
        complete:function(){//不管成功还是失败 都会进这个函数
        }
    })

}

/**显示加入房间提示*/
function showJoinHomeAlert(newHome){
    swal({
        title : "提示",
        text : "确认加入该房间？",
        type : "warning",
        buttons : {
            button1 : {
                text : "取消",
                value : false,
            },
            button2 : {
                text : "确认",
                value : true,
            }
        },
    }).then(function(value) {   //这里的value就是按钮的value值，只要对应就可以啦
        if (value) {
            //修改加入的房间
            joinHome(newHome);
        } else {
        }
    });
}

//加入房间请求处理
function joinHome(homeNumber){
    $.ajax({
        type:"PUT",// get或者post
        url:"/api/account/joinHome/"+ homeNumber,// 请求的url地址
        contentType:"application/json;charset=utf-8",
        timeout:15000,//3秒后提示错误
        beforeSend:function(){
            // 发送之前就会进入这个函数
            // return false 这个ajax就停止了不会发 如果没有return false 就会继续
        },
        success:function(data){ // 成功拿到结果放到这个函数 data就是拿到的结果
            console.debug("==>data:"+data)
            if(data.code == 200){
                swal("加入房间成功");
                homeName = data.data.nickName;
                homeNumber = data.data.homeNumber;
                $("h2").text(homeName);
            }else if(data.code>1600 && data.code<1699){
                swal(data.data);
            }else {
                swal({text: "请求出错", icon: "error"});
                console.debug("==>"+data.data);
            }
        },
        error:function(){//失败的函数
            swal({text: "请求出错", icon: "error"});
        },
        complete:function(){//不管成功还是失败 都会进这个函数
        }
    })
}

function register(fingerprint){
    console.debug("==>fingerprint:"+fingerprint)
    var data = {"deviceToken": fingerprint};
    $.ajax({
        type:"POST",// get或者post
        url:"/api/account/login",// 请求的url地址
        data:JSON.stringify(data),//请求的参数
        dataType:"JSON",//json写了jq会帮我们转换成数组或者对象 他已经用JSON.parse弄好了
        contentType:"application/json;charset=utf-8",
        timeout:15000,//3秒后提示错误
        beforeSend:function(){
            // 发送之前就会进入这个函数
            // return false 这个ajax就停止了不会发 如果没有return false 就会继续
        },
        success:function(data){ // 成功拿到结果放到这个函数 data就是拿到的结果
            console.debug("==>data:"+data)
            if(data.code == 200){
                username = data.data.nickName;
                mineNumber = data.data.homeNumber;
                if(username){
                    $("h2").text(username)
                }
                connect();
            }else {
                console.debug("==>"+data.data);
            }
        },
        error:function(){//失败的函数
        },
        complete:function(){//不管成功还是失败 都会进这个函数
        }
    })
}
//关闭页面则关闭服务器连接
window.onbeforeunload=function(e){
    if(stompClient){
        stompClient.disconnect();
    }
}

//获取url参数
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return null;
}
/**jia*/
function rsaEncrypt(str){
    return str;
    var publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAL6BHMBRMSQd/CcR1IbBw1Y5CUA5uawJLWWBEn9MnTPAdcbTk7EWNpn+euLcYDvHifU9Oug1ohfCNVRmXBZceOkCAwEAAQ==";
    if(null!=str && str.toString().trim().length>0){
        try{
            str = "aaa";
            var encrypt = new JSEncrypt();
            var pu1 = Base64.decode(publicKey);
            console.debug("==>pu1:"+pu1);
            encrypt.setPublicKey(pu1);
            var data = encrypt.encrypt(str);
            console.debug("==>data:"+data);
            return Base64.encode(data);
        }catch (e) {
            console.log("==>error:"+e.toString())
            console.error(e)
        }
    }else{
        return ""
    }
}
/**jie*/
function rsaDecrypt(str){
    return str;
    var privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAsrzKFLuELYPeWxMhjjs+GyZN3RTCuKfMZleouD29BB2atX6Ip39XVY+Attag7bqu6gd8j/5yBTqm6bRibzAdYwIDAQABAkBYpx8sbV3AHZoaXkDUhTnmyXbxYyy53jCZynza9XRdJ2QYfyHLpD+gw2+wosQG2PU3mLVNKyvqetgt6eZLKgPhAiEA8etklFOnXLeoh/Ne6h+39cHNTwe/6IH/2dPHT7urmTkCIQC9I/t5WpWwY/I23nIF1IoeKd2uaEgrDpaHhgZeD6R3ewIhAMaylFXPr7LTljSzi824Z5wOpda3gsQxojcDXrz6Y6LhAiEAmMVijr+rHqFr+AOup6TntrtsMj5K5HRRA8AujnUmC9cCIQDpoEPNRWx+vwsuHgph+8M2rmNQGyovjl9AA05gq/DS7A==";
    if(null!=str && str.toString().trim().length>0){
        try{
            var decrypt = new JSEncrypt();
            decrypt.setPrivateKey(Base64.decode(privateKey));
            return decrypt.decrypt(Base64.decode(str));
        }catch (e) {
            console.log("==>error:"+e.toString())
            console.error(e)
        }
    }else{
        return ""
    }
}

/*
var Base64 = {

    // private property
    _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

    // public method for encoding
    encode: function(input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;

        input = Base64._utf8_encode(input);

        while (i < input.length) {

            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

        }

        return output;
    },

    // public method for decoding
    decode: function(input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        while (i < input.length) {

            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

        }

        output = Base64._utf8_decode(output);

        return output;

    },

    // private method for UTF-8 encoding
    _utf8_encode: function(strdata) {
        var str = strdata.toString();
        str = str.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < str.length; n++) {

            var c = str.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    },

    // private method for UTF-8 decoding
    _utf8_decode: function(utftext) {
        var string = "";
        var i = 0;
        var c = 0;
        var c1 = 0;
        var c2 = 0;
        var c3 = 0;

        while (i < utftext.length) {

            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }

        }

        return string;
    }

}*/
