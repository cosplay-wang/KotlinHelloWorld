var bridgeClass = {
    __CONF__: {
        appContext: "jscomm",
        appUrl: "ekwing:abc"
    },
    jsEventHook: {
        goback: function() {
            bridgeClass.toLocalEvent("goback")
        }
    },
    toJsEvent: function(e, t) {
        if (bridgeClass.jsEventHook[e] && "function" === typeof bridgeClass.jsEventHook[e]) {
            t = bridgeClass.parseParams(t);
            bridgeClass.jsEventHook[e](t)
        } else {
            var r = new Error('bridgeClass.jsEventHook["' + e + '"] is undefined.');
            bridgeClass.log(r);
            return false
        }
    },
    toLocalEvent: function(e, t, r) {
        if (typeof t === typeof {} && t.url) {
            t.url = bridgeClass.rewireUrl(t.url)
        }
        if (typeof t !== typeof "string") {
            t = JSON.stringify(t)
        }
        if (r) {
            r("debugTrace[toLocalEvent][Params]", t)
        }
        if (!window[bridgeClass.__CONF__.appContext]) {
            var a;
            var o = {
                event: e,
                params: t
            };
            var n = bridgeClass.__CONF__.appUrl.concat("?".concat(JSON.stringify(o)));
            if (r) {
                r("debugTrace[ios][url]", n)
            }
            a = document.createElement("iframe");
            a.setAttribute("src", n);
            a.setAttribute("style", "display:none;");
            a.setAttribute("height", "0px");
            a.setAttribute("width", "0px");
            a.setAttribute("frameborder", "0");
            document.body.appendChild(a);
            a.parentNode.removeChild(a);
            a = null
        } else {
            if (r) {
                r("debugTrace[Android][toLocalEvent]", e, t)
            }
            window[bridgeClass.__CONF__.appContext].toLocalEvent(e, t)
        }
    },
    log: console.log ||
    function() {},
    parseParams: function(e) {
        var t = e;
        try {
            t = JSON.param(e)
        } catch(r) {
            t = e
        }
        return e
    },
    rewireUrl: function(e) {
        if (GC && GC("REQ_BASE_URL")) {
            if (!/^http[s]{0,1}:\/\/.*/.test(e)) {
                e = GC("REQ_BASE_URL").concat(e);
                return e.replace("//", "/")
            }
        }
        return e
    },
    __CB__: function(e) {
        var t = Math.floor(Math.random() * 100 * 100);
        var r = (new Date).getTime() + "" + t;
        if (!bridgeClass.jsEventHook["callback" + r]) {
            r += Math.floor(Math.random() * 10 * 10)
        }
        bridgeClass.jsEventHook["callback" + r] = function() {
            e.apply(this, arguments);
            delete bridgeClass.jsEventHook["callback" + r]
        };
        return "callback" + r
    }
};
if (GC("debug")) {
    require(["bridge_debug"])
}





















var bridgeClass = {
    __CONF__: {
        appContext: "jscomm",
        appUrl: "ekwing:abc"
    },
    jsEventHook: {
        goback: function() {
            bridgeClass.toLocalEvent("goback")
        }
    },
    toJsEvent: function(e, t) {
        if (bridgeClass.jsEventHook[e] && "function" === typeof bridgeClass.jsEventHook[e]) {
            t = bridgeClass.parseParams(t);
            bridgeClass.jsEventHook[e](t)
        } else {
            var r = new Error('bridgeClass.jsEventHook["' + e + '"] is undefined.');
            bridgeClass.log(r);
            return false
        }
    },
    toLocalEvent: function(e, t, r) {
        if (typeof t === typeof {} && t.url) {
            t.url = bridgeClass.rewireUrl(t.url)
        }
        if (typeof t !== typeof "string") {
            t = JSON.stringify(t)
        }
        if (r) {
            r("debugTrace[toLocalEvent][Params]", t)
        }
        if (!window[bridgeClass.__CONF__.appContext]) {
            var a;
            var o = {
                event: e,
                params: t
            };
            var n = bridgeClass.__CONF__.appUrl.concat("?".concat(JSON.stringify(o)));
            if (r) {
                r("debugTrace[ios][url]", n)
            }
            a = document.createElement("iframe");
            a.setAttribute("src", n);
            a.setAttribute("style", "display:none;");
            a.setAttribute("height", "0px");
            a.setAttribute("width", "0px");
            a.setAttribute("frameborder", "0");
            document.body.appendChild(a);
            a.parentNode.removeChild(a);
            a = null
        } else {
            if (r) {
                r("debugTrace[Android][toLocalEvent]", e, t)
            }
            window[bridgeClass.__CONF__.appContext].toLocalEvent(e, t)
        }
    },
    log: console.log ||
    function() {},
    parseParams: function(e) {
        var t = e;
        try {
            t = JSON.param(e)
        } catch(r) {
            t = e
        }
        return e
    },
    rewireUrl: function(e) {
        if (GC && GC("REQ_BASE_URL")) {
            if (!/^http[s]{0,1}:\/\/.*/.test(e)) {
                e = GC("REQ_BASE_URL").concat(e);
                return e.replace("//", "/")
            }
        }
        return e
    },
    __CB__: function(e) {
        var t = Math.floor(Math.random() * 100 * 100);
        var r = (new Date).getTime() + "" + t;
        if (!bridgeClass.jsEventHook["callback" + r]) {
            r += Math.floor(Math.random() * 10 * 10)
        }
        bridgeClass.jsEventHook["callback" + r] = function() {
            e.apply(this, arguments);
            delete bridgeClass.jsEventHook["callback" + r]
        };
        return "callback" + r
    }
};
if (GC("debug")) {
    require(["bridge_debug"])
}