/*
paragraph,blockquote,blockcode,table,head,ullist,ollist,image,
*/

const ALL_MDTYPE = new Set(["paragraph", "blockquote", "blockcode", "table", "head", "ullist", "ollist", "image",]);
const LF = '\n';

class MNode {
    constructor(type, meta, parent, excludes) {
        //md 类型, 不同的类型决定了将要dom结构
        this.type = type || 'paragraph';
        //父节点
        this.parent = parent;

        //本类型子类禁用的列表, 是本节点的禁用类和父节点禁用列表并集
        if (parent && excludes) {
            excludes = new Set([...parent.excludes, ...excludes])
        }
        this.excludes = excludes || new Set();

        //存放子节点
        this.children = [];
        //元数据文本
        this.meta = meta || '';
    }


    /**
     * 计算并获取最终的元数据文本, 用于存储Markdown原始文本内容.
     */
    getMeta() {
        return this.meta;
        /* //paragraph且没有子节点->自身的meta就是最终的meta
         if (this.type == 'paragraph' && this.children.length == 0) {
             return this.meta;
         }

         //标题
         //不存在子节点的
         if (this.type == 'head') {

         }*/
    }


}

class Paragraph extends MNode {
    constructor(meta, parent) {
        super(null, meta, parent, ALL_MDTYPE);
    }
}

class MHead extends MNode {
    constructor(level, meta) {
        super('head', meta, null, ALL_MDTYPE);
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


class Blockquote extends MNode {
    constructor(parent) {
        super('blockquote', null, parent, null);
    }

    getMeta() {
        let m = '';
        for (let i = 0; i < this.children.length; i++) {
            let c = this.children[i];
            m += '> ' + c.getMeta() + LF;
        }
    }
}

//方法区
function inputHandle($dom,currentMNode,mNodeMap) {
    let input = $dom.text();
    console.log(input);

    let headLikeReg = /^(#{1,6})\s+(.+)(?:\n|$)?/;
    let m = headLikeReg.exec(input);
    if (m) {
        //几个#代表几级标题
        let lvl = m[1].length;
        let cnt = m[2];
        let mHead = new MHead(lvl, cnt);
        let cid = incrId();
        let hn = 'h' + lvl;
        mHead.$el = $('<' + hn + ' contenteditable="true" mdtype="head" cid="' + cid + '"></' + hn + '>');
        mHead.$el.text(cnt);
        //判断当前node是否允许含有head类型子节点
        if (currentMNode.excludes.has(mHead.type)) {
            //替换
            currentMNode.$el.replaceWith(mHead.$el);
            //mHead.$el.focus();
            moveCursorTo(mHead.$el,cnt.length);
            mNodeMap.delete(currentMNode);
            mNodeMap.set(cid, mHead);
            currentMNode = null;        //置空便于回收
        } else {
            //放入子节点数组中
            currentMNode.children.push(mHead);
        }

    }
}

function moveCursorTo($dom,start){
    $dom.focus();
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
    }

}

//

let cid = -1;
let incrId = function () {
    return cid += 2;
}

$(function () {
    const $meditor = $('.md_editor');
    let mNodeMap = new Map();

    //页面加载动作
    let para = new Paragraph('', null);
    let cid = incrId();
    para.$el = $('<p cid="' + cid + '" mdtype="paragraph" contenteditable="true"></p>');
    //添加到实例容器
    mNodeMap.set(cid, para);
    //添加到dom容器
    $meditor.append(para.$el);
    para.$el.focus();       //添加到页面后, 聚焦才有效果
    let isPinyin = false;
    para.$el.on('compositionstart', function () {
        isPinyin = true;
        console.log('start');

    }).on('compositionend', function () {
        inputHandle($(this),para,mNodeMap);
        isPinyin = false
    }).on('input', function () {

        if (!isPinyin) inputHandle($(this),para,mNodeMap);

    })


})

