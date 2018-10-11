/*
paragraph,blockquote,blockcode,table,head,ullist,ollist,image,
*/

const ALL_MDTYPE = new Set(["paragraph", "blockquote", "blockcode", "table", "head", "ullist", "ollist", "image",]);
const LF = '\n';

class MNode {
    constructor(type,meta, parent, excludes) {
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
        this.meta = meta||'';
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

class MHead extends MNode {
    constructor(level, meta) {
        super('head',meta, null, ALL_MDTYPE);
        //标题级别
        this.level = level || 1;
    }
    getMeta(){
        let m='';
        for(let i=0;i<this.level;i++) {
            m += "#";
        }
        return m+=' '+this.meta+LF;
    }
}


class Blockquote extends MNode{
    constructor(parent){
        super('blockquote', null, parent, null);
    }

    getMeta(){
        let m='';
        for (let i = 0; i < this.children.length; i++) {
          let c = this.children[i];
            m += '> ' + c.getMeta()+LF;
        }
    }
}



