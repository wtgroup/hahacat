// let JSEncrypt = require('./jsencrypt.js');
// let jsEncrypt = new JSEncrypt();
//
// let pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWc5AJyqFagZadLcrX7sDphQM0t1sAKhVslbMyfMe5yWcfadNaQBY_z6v7zxB3gHsdsViLp5EdxjZNz0DNYh3ljYKWDTsqTsf9nx5rj18eKaT4PQZa0hFnJjNppcj2SlQsUdY6RqoiW9lJLKnL20UW9ndwS-Mr6YMFQyC00Qg73wIDAQAB";
//
// jsEncrypt.setPublicKey(pubKey);
// let enc = jsEncrypt.encrypt("123456");
// console.log(enc);

var decoder$1;
var decoder$2;
var Base64 = {
    decode: function (a) {
        return this.decode0(a, false);
    },
    decodeUrlSafe: function (a) {
        return this.decode0(a, true);
    },
    decode0:function(src,urlSafe){
        var i;
        var ignore = "= \f\n\r\t\u00A0\u2028\u2029";        // (, )
        if (!urlSafe && decoder$1 === undefined) {
            var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
            decoder$1 = Object.create(null);
            for (i = 0; i < 64; ++i) {
                decoder$1[b64.charAt(i)] = i;
            }
            for (i = 0; i < ignore.length; ++i) {
                decoder$1[ignore.charAt(i)] = -1;
            }
        }
        if (urlSafe && decoder$2 === undefined) {
            var urlB64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
            decoder$2 = Object.create(null);
            for (i = 0; i < 64; ++i) {
                decoder$2[urlB64.charAt(i)] = i;
            }
            for (i = 0; i < ignore.length; ++i) {
                decoder$2[ignore.charAt(i)] = -1;
            }
        }

        //编码映射器
        var decoder;
        if (urlSafe) {
            decoder = decoder$2;
        }else{
            decoder = decoder$1;
        }

        var out = [];
        var bits = 0;
        var char_count = 0;
        for (i = 0; i < src.length; ++i) {
            var c = src.charAt(i);
            if (c == "=") {
                break;
            }
            c = decoder[c];
            if (c == -1) {
                continue;
            }
            if (c === undefined) {
                throw new Error("Illegal character at offset " + i);
            }
            bits |= c;
            if (++char_count >= 4) {
                out[out.length] = (bits >> 16);
                out[out.length] = (bits >> 8) & 0xFF;
                out[out.length] = bits & 0xFF;
                bits = 0;
                char_count = 0;
            }
            else {
                bits <<= 6;
            }
        }
        switch (char_count) {
            case 1:
                throw new Error("Base64 encoding incomplete: at least 2 bits missing");
            case 2:
                out[out.length] = (bits >> 10);
                break;
            case 3:
                out[out.length] = (bits >> 16);
                out[out.length] = (bits >> 8) & 0xFF;
                break;
        }
        return out;

    },
    re: /-----BEGIN [^-]+-----([A-Za-z0-9+\/=\s]+)-----END [^-]+-----|begin-base64[^\n]+\n([A-Za-z0-9+\/=\s]+)====/,
    re4UrlSafe: /-----BEGIN [^-]+-----([A-Za-z0-9+\/=\s]+)-----END [^-]+-----|begin-base64[^\n]+\n([A-Za-z0-9\-_\s]+)====/,
    unarmor: function (a) {
        var urlSafe=false;
        var m = Base64.re.exec(a);
        if(!m) {
            Base64.re4UrlSafe.exec(a);
            urlSafe=true;
        }
        if (m) {
            if (m[1]) {
                a = m[1];
            }
            else if (m[2]) {
                a = m[2];
            }
            else {
                throw new Error("RegExp out of sync");
            }
        }
        return !urlSafe ? Base64.decode(a) : Base64.decodeUrlSafe(a);
    }
};

window.JfBase64=Base64;
