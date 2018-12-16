/*
paragraph,blockquote,blockcode,table,head,ullist,ollist,image,
*/
const Constant = {
    LINE_SEPARATOR: "\n",
    /**单个字符宽度*/
    CHAR_WIDTH: 8,
    NON_LATIN_CHAR_WIDTH: 16,
    CHAR_HEIGHT: 26
};
//--md types--//
let _mtypes = ["PARAGRAPH", "QUOTE", "BLOCK_CODE", "TABLE", "HEAD", "ullist", "ollist", "image",];
Constant.MTYPE = {
    ALL: new Set(_mtypes),
    PARAGRAPH: _mtypes[0],
    QUOTE: _mtypes[1],
    BLOCK_CODE: _mtypes[2],
    TABLE: _mtypes[3],
    HEAD: _mtypes[4],
    //todo 补全
};
Constant.CELL={
    TYPE:{
        LATIN: 'latin',
        NON_LATIN: 'non_latin',
        ITALIC: 'italic',
        BOLD: 'bold',
        INLINE_CODE: 'inline_code',
    },
    //CELL_OPEN : "\\*\\*|\\*|__|_|~~|`",
    //CELL_REG :new RegExp('([\\s\\S]*?)(('+this.CELL_OPEN+').*\\3)(.*)')        //([\s\S]*?)((\*\*|\*|__|_|~~|`).*\3).*

};
//==正则==//
Constant.REG = {
    SPLIT_CELL:new RegExp('([\\s\\S]*?)((\\*\\*|\\*|__|_|~~|`).*\\3)(.*)'),
    //区分全角字符(暂时是中(含繁体)日韩)和半角字符
    SPLIT_FULL_HALF_WIDTH_TXT:new RegExp('([^\u2E80-\u9FFF]*)([\u2E80-\u9FFF]*)')
};






class ModeFactory {
    constructor(context) {

        //上下文环境
        this.context = {
            $mEditor: null,
            modeMap: new Map(),
            currentMode: null,
        }
        $.extend(this.context, context);
        let that = this;
        this._validateContext();
        this.context.$caret[0].toggleShow = function () {
            if (that.context.$caret[0].isShow) {
                that.context.$caret.css('visibility', 'hidden');
                that.context.$caret[0].isShow = false;
            } else {
                that.context.$caret.css('visibility', '');
                that.context.$caret[0].isShow = true;
            }
        }

        that.context.$textarea.on('blur', function () {
            console.log('textarea blur');
            that.context.$mEditor.trigger('blur');
        }).on('input', function (e) {
            console.log('textarea input');
            //内容剪切到当前Mode
            that.context.currentMode.inputHandle(this.innerText);
            this.innerText = '';
        })

        //脱字符闪烁
        let caretTimer = window.setInterval(this.context.$caret[0].toggleShow, 500);
        this.context.$mEditor.on("click", function (e) {
            e = e || event || window.event;
            console.log("e.target", e.target);
            if (e != null && e.target != null && e.target.hasAttribute("mid")) {
                that.context.currentMode = that.context.modeMap.get(e.target.attr("mid"));
            }

            //重启脱字符闪烁
            that.context.$caret.removeClass('m_editor-blur');
            window.clearInterval(caretTimer);
            caretTimer = window.setInterval(that.context.$caret[0].toggleShow, 500);

            let scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            let scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            let x = e.pageX || e.clientX + scrollX;
            let y = e.pageY || e.clientY + scrollY;
            //alert('x: ' + x + '\ny: ' + y);
            console.log({'x': x, 'y': y});

            //Test
            that.context.$caret.css({'left': x - Constant.CHAR_WIDTH + 'px', 'top': y - Constant.CHAR_HEIGHT + 'px'});
            that.context.$textarea.css({
                'left': x - Constant.CHAR_WIDTH + 'px',
                'top': y - Constant.CHAR_HEIGHT + 'px'
            });
            that.context.$textarea.focus();


        }).on('blur', function () {
            console.log('blur');
            //textarea处于聚焦状态, 即处于输入状态
            console.log(this.isMouseover);
            //失去焦点 AND 鼠标离开了, 避免因为聚焦textarea导致meditor失去焦点导致错误的行为
            if (!this.isMouseover) {
                //关闭脱字符闪烁, 并模糊显示
                window.clearInterval(caretTimer);
                that.context.$caret.css('visibility', '');
                that.context.$caret.addClass('m_editor-blur');
            }
        }).on('focus', function () {
            console.log('focus');

        }).on('mouseover', function () {
            console.log('mouseover');
            this.isMouseover = true;
        }).on('mouseout', function () {
            console.log('mouseout');
            this.isMouseover = false;
        });

        this.context.$mEditor.append(this.context.$textarea);
        this.context.$mEditor.append(this.context.$caret);


        this.mid = -1;

    }

    _validateContext() {
        if (this.context.$mEditor == null) {
            throw new Error("缺少`$mEditor`");
        }
        if (this.context.$textarea == null) {
            throw new Error("缺少`$textarea`");
        }
        if (this.context.$caret == null) {
            throw new Error("缺少`$caret`");
        }
    }

    /**
     * {
     *   modeClass:Mode具体类型,
     *   level:标题级别数字,
     *   meta:元数据,
     * }
     *
     * @param args
     * @returns {*}
     */
    createMode(args) {
        if (args == null || args.modeClass == null) {
            console.log("not specify mode class")
            return null;
        }
        //要求是 Mode 及其子类
        if (!(Mode.isPrototypeOf(args.modeClass) || args.modeClass === Mode)) {
            console.log("not supported mode class: " + args.modeClass);
            return null;
        }

        let mode;
        let mid = this.nextMid();
        if (args.modeClass === Paragraph) {
            let para$el = $('<p mid="' + mid + '" MTYPE="PARAGRAPH" contenteditable="true"></p>');
            mode = new args.modeClass(args.meta, args.parent, para$el);
        } else if (args.modeClass === MHead) {
            let hn = 'h' + args.level;
            let mh$el = $('<' + hn + ' mid="' + mid + '" MTYPE="HEAD" contenteditable="true"></' + hn + '>');
            mode = new MHead(args.level, args.meta, mh$el);
        }

        this.context.modeMap.set(mid, mode);
        if (mode.previous == null) {
            this.context.$mEditor.append(mode.$el);
            mode.$el.focus();
        } else {
            //Note:previous 必须已经在dom容器中了才有效
            mode.previous.$el.after(mode.$el);
        }

        return mode;
    }

    replaceWith(oldMode, newMode) {
        oldMode.$el.replaceWith(newMode.$el);
        newMode.previous = oldMode.previous;
        newMode.next = oldMode.next;
        this.context.modeMap.delete(oldMode.getMid());
        this.context.modeMap.set(newMode.getMid(), newMode);
        oldMode = null;    //置空便于回收
    }


    nextMid() {
        return this.mid += 2;
    }

    inputHandle($dom) {
        let input = $dom.text();
        console.log(input);

        let headLikeReg = /^(#{1,6})\s+(.+)(?:\n|$)?/;
        let m = headLikeReg.exec(input);
        if (m) {
            //几个#代表几级标题
            let lvl = m[1].length;
            let cnt = m[2];
            //let mHead = new MHead(lvl, cnt, null, context);
            let mHead = this.createMode({level: lvl, meta: cnt});
            mHead.$el.text(args.meta);

            //判断当前node是否允许含有head类型子节点
            if (mHead.excludes.has(mHead.type)) {
                //替换
                //mHead.$el.replaceWith(mHead.$el);
                this.replaceWith(this.context.currentMode, mHead);
                //mHead.$el.focus();
                moveCursorToEnd(mHead.$el);
            } else {
                //放入子节点数组中
                mHead.children.push(mHead);
            }
            //
            mHead.binding();

        }
    }





}


class Mode {
    constructor(type, meta, parent, excludes, $el, context) {
        //md 类型, 不同的类型决定了将要dom结构
        this.type = type || 'PARAGRAPH';
        //父节点
        this.parent = parent;

        //本类型子类禁用的列表, 是本节点的禁用类和父节点禁用列表并集
        if (parent && excludes) {
            this.excludes = new Set([...parent.excludes, ...excludes])
        }
        this.excludes = this.excludes || new Set();

        //存放子节点
        this.children = [];
        //元数据文本
        this.meta = meta!=null? typeof meta==='string' && meta.split('') : [];
        //对应的jQuery节点
        this.$el = $el;
        //环境参数
        this.context = context || {};
        //前一个节点
        //this.previous;
        //后一个节点
        //this.next;
    }


    /**
     * 计算并获取最终的元数据文本, 用于存储Markdown原始文本内容.
     */
    getMeta() {
        return this.meta;
        /* //paragraph且没有子节点->自身的meta就是最终的meta
         if (this.type == 'PARAGRAPH' && this.children.length == 0) {
             return this.meta;
         }

         //标题
         //不存在子节点的
         if (this.type == 'HEAD') {

         }*/
    }

    getMid() {
        if (this.$el == null) {
            return null;
        }
        return this.$el.attr("mid");
    }

    binding() {
        let that = this;
        let isPinyin = false;
        let lastIsEmpty = false;
        this.$el.on('compositionstart', function (e) {
            console.log('compositionstart', this.innerText);
            isPinyin = true;
            console.log('compositionstart');

        }).on('compositionend', function (e) {
            console.log('compositionend', this.innerText);
            //inputHandle($(this),that.context);
            isPinyin = false
        }).on('input', function (e) {
            lastIsEmpty = this.innerText == '';
            console.log('input', this.innerText);
            if (!isPinyin) {
                //inputHandle($(this),that.context);

            }

        }).on('keydown', function (e) {
            console.log('keydown', e.keyCode);
            let newBlock = false;
            if (e.keyCode == 13) {      //回车键
                e.preventDefault();
                //TODO 光标所在位置直到该模块末尾的内容要剪切到新建的下方标签里
                //当前块的内容子光标位置, 一分为二, 后部分剪切, new Paragraph()的内容
                newBlock = true;
            }
            if (e.keyCode == 40) {      //方向下键
                //内容不为空时, 才执行
                if (this.innerText != '') {
                    newBlock = true;

                }
            }
            if (newBlock) {
                inputHandle($(this), that.context);
                that._initStubMNode(true);
            }

            //利用keyup在input事件之后, 且若回退没有内容后, 下次再回退就不会触发input
            if (lastIsEmpty && (e.keyCode == 8 || e.keyCode == 46)) {
                //=>上次input就已经空了, 本次按了Backspace/Delete
                //删除本节点, 替换为P标签
                if (!(that instanceof Paragraph)) {
                    that._initStubMNode(false);
                }

            }

        }).on('keyup', function (e) {
            console.log('keyup', e.keyCode);
        })
    }

    //初始化一个站位MNode, 目前等价于Paragraph
    _initStubMNode(append) {
        let that = this;
        let cid = incrId();
        let para = new Paragraph('', null, $('<p cid="' + cid + '" mdtype="PARAGRAPH" contenteditable="true"></p>'),
            that.context);
        if (append) {
            that.$el.after(para.$el);
            that.next = para;
            that.context.mNodeMap.set(cid, para);
        } else {
            that.$el.replaceWith(para.$el);
            //TODO 替换 that.context.mNodeMap.get(cid)
        }
        that.context.currentMNode = para;
        para.$el.focus();
        para.binding();
    }


}

class Paragraph extends Mode {
    constructor(meta, parent, $el, context) {
        super(Constant.MTYPE.PARAGRAPH, meta, parent, Constant.MTYPE.ALL, $el, context);
        //this.cells = [];
    }


}


class MHead extends Mode {
    constructor(level, meta, $el, context) {
        super('HEAD', meta, null, Constant.MTYPE.ALL, $el, context);
        //标题级别
        this.level = level || 1;
    }

    getMeta() {
        let m = '';
        for (let i = 0; i < this.level; i++) {
            m += "#";
        }
        return m += ' ' + this.meta + LF;
    }


}


class Blockquote extends Mode {
    constructor(parent) {
        super('QUOTE', null, parent, null);
    }

    getMeta() {
        let m = '';
        for (let i = 0; i < this.children.length; i++) {
            let c = this.children[i];
            m += '> ' + c.getMeta() + LF;
        }
    }
}

// class MLine

class Cell {
    constructor(mode) {
        /**cell类型, 如italic,bold, inline-code...*/
        //this.type = type;
        // /**隶属于哪个Mode实例*/
        this.mode = mode;
        //this.meta = [];
        this.start = -1;
        this.end = -1;
        this.previous = null;
        this.next = null;
        this.caretLeft = false;
        this.caretRight = false;
        this.caretOver = false;
        this.viewCharNum = 0;
        this.charWidth = Constant.CHAR_WIDTH;
        this.metaCharNum = this.end === -1 ? 0 : this.end - this.start + 1;
        this.children = [];
    }



    /**不同的Cell有独特的聚合方式, 如inline code内部不会聚斜体, 加粗*/
    static clustering(parent,mode,previous){
        if (parent == null) {
            throw new Error('No arg: `parent`');
        }

        let src = this.getMeta();

        let m = src.match(Constant.REG.SPLIT_CELL);


        let residualSrc='';



        if (m == null) {
            //整体都是纯文本
            Cell.clusteringPlainText(src,parent,mode,previous);

        }else if(m[2]){
            //找到非纯文本
            if(m[1]) {
                //开头的纯文本
                previous=Cell.clusteringPlainText(m[1], parent, mode,previous);
            }

            let openMd = m[3];
            let plnTxt = m[2].substring(openMd.length, m[2].length - openMd.length);

            Cell.clusteringNonPlainText(parent, mode, previous, openMd, plnTxt);

            residualSrc = m[4];
        }

        console.log(cells);
        console.log(residualSrc);

    }

    static initPlainText(plnTxtCell,previous,len) {
        if (previous == null) {
            plnTxtCell.start = 0;
        } else {
            plnTxtCell.start = previous.end + 1;
            previous.next = plnTxtCell;
            plnTxtCell.previous = previous;
        }
        plnTxtCell.end = plnTxtCell.start+len-1;
        plnTxtCell.metaCharNum = plnTxtCell.end - plnTxtCell.start + 1;

        return plnTxtCell;      //最后一个cell, 对于外层同级别来说, 这就是previous了
    };

    static clusteringPlainText(src,parent,mode,previous) {
        while (src) { //纯文本 plain text
            //需要区分出全角字符和半角字符
            let fh = src.match(Constant.REG.SPLIT_FULL_HALF_WIDTH_TXT);
            if (fh[0]) {
                if (fh[1]) {
                    //半角字符 组
                    let l = fh[1].length;
                    let hwtxt = new HalfWidthText(mode);
                    //初始化, 并更新previous
                    previous=Cell.initPlainText(hwtxt, previous, l);
                    parent.children.push(hwtxt);
                    hwtxt.parent = parent;
                }
                if (fh[2]) {
                    //全角字符 组
                    let fwtxt = new FullWidthText(mode);
                    previous=Cell.initPlainText(fwtxt, previous);
                    parent.children.push(fwtxt);
                }
                src = src.substr(0, fh[0].length);
            }
        }

        //返回最后的cell
        return previous;
    };

    static clusteringNonPlainText(parent, mode, previous, openMd, plnTxt) {

    }



    buildHtml() {
    }

    onCaretLeft() {
    }

    onCaretRight() {
    }

    onCaretOver() {
    }

    getMeta() {
        if (this.metaCharNum > 0) {
            return this.mode.meta.slice(this.start, this.end + 1).join("");
        }
        return null;
    }

    getTextWidth() {
        return this.viewCharNum * this.charWidth;
    }


}

Cell.symWrapCells={
    "**":function () {

    },
    "*":function () {

    },
    "__":function () {

    },
    "_":function () {

    },
    "~~":function () {

    }
}



/**纯文本*/
class PlainText extends Cell{
    constructor(mode){
        super(mode);
    }

}

/**普通文本, 半角字符, 如: 拉丁字符*/
class HalfWidthText extends PlainText {
    constructor(mode) {
        super(mode);
    }
}

/**普通文本, 全椒字符, 如非拉丁字符, 中日韩*/
class FullWidthText extends PlainText {
    constructor(mode) {
        super(mode);
        this.charWidth = Constant.NON_LATIN_CHAR_WIDTH;
    }
}

class Bold extends Cell {
    constructor(mode) {
        super(mode);
    }
}

class Italic extends Cell {
    constructor(mode) {
        super(mode);
        // this.preWrap = "*";
        // this.postWrap = "*";
    }
}

class InlineCode extends Cell {
    constructor(mode) {
        super(mode);
    }
}


//公共方法区
// function inputHandle($dom, context) {
//     let input = $dom.text();
//     console.log(input);
//
//     let headLikeReg = /^(#{1,6})\s+(.+)(?:\n|$)?/;
//     let m = headLikeReg.exec(input);
//     if (m) {
//         //几个#代表几级标题
//         let lvl = m[1].length;
//         let cnt = m[2];
//         let mHead = new MHead(lvl, cnt, null, context);
//         //绑定事件处理
//         // mHead.$el
//
//         //判断当前node是否允许含有head类型子节点
//         if (context.currentMNode.excludes.has(mHead.type)) {
//             //替换
//             context.currentMNode.$el.replaceWith(mHead.$el);
//             //mHead.$el.focus();
//             moveCursorToEnd(mHead.$el);
//             context.mNodeMap.delete(context.currentMNode);
//             context.mNodeMap.set(cid, mHead);
//             context.currentMNode = null;        //置空便于回收
//         } else {
//             //放入子节点数组中
//             context.currentMNode.children.push(mHead);
//         }
//         //
//         context.currentMNode = mHead;
//         mHead.binding();
//
//     }
// }

function moveCursorToEnd($dom) {

    let textbox = $dom[0];
    let sel = window.getSelection();
    let range = document.createRange();
    //选择节点中的所有内容==全选
    range.selectNodeContents(textbox);
    //collapses the range to end
    range.collapse(false);
    sel.removeAllRanges();
    //更新range
    sel.addRange(range);

    /*$dom.focus();
    if(!start){
        //start = $dom.text().length;
        start = 0;
    }
    if (document.selection) {
        let sel = $dom[0].createTextRange();
        sel.moveStart('character',start);
        sel.collapse();
        sel.select();
    } else if (typeof $dom[0].selectionStart == 'number' && typeof $dom[0].selectionEnd == 'number') {
        $dom[0].selectionStart = $dom[0].selectionEnd = start;
    }*/

}

//

// let cid = -1;
// let incrId = function () {
//     return cid += 2;
// }

$(function () {
    //初始化隐藏文本域
    let $textarea = $('<textarea class="m_textarea" wrap="off" autocorrect="off" autocapitalize="off" spellcheck="false" style="top: 0px; height: ' + Constant.CHAR_HEIGHT + 'px; width: ' + Constant.CHAR_WIDTH + 'px; left: 15px;"></textarea>');
    let $caret = $('<div class="m_caret" style="width: 8px; height: ' + Constant.CHAR_HEIGHT + 'px;"></div>');

    let mf = new ModeFactory({
        $mEditor: $('.m_editor'),
        $textarea: $textarea,
        $caret: $caret,
    });

    //页面加载动作
    // let para = mf.createMode({modeClass: Paragraph, meta: ''});

    //context.currentMNode = para;
    // para.$el.focus();       //添加到页面后, 聚焦才有效果
    // para.binding();

})

