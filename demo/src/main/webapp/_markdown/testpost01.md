---
title: 标题
author: 
date:
category: cateA
tags: [a,b,c..]
comments: true || false
images: 链接
---


# 我的模板
## 标题二
```

this is codes

public function()

 // 将代码内容按照换行符切割成数组
        /*这种处理策略, 尾部空行将被剔除掉*/
        String newLine = Profile.NEW_LINE;
        String[] codeLines = codeContent.split(newLine);
        // 遍历数组
        for (int i = codeLines.length - 1; i >= 0; i--) {
            // 生成行号
            String numSpan = "<span class=\"line\">" + (i+1) + "</span><br>";
            // 生成代码行
            String codeSpan = "<span class=\"line\">" + codeLines[i] + "</span><br>";
            
            blockCodeDoc.select("pre").get(0).append(numSpan);
            blockCodeDoc.select("pre").get(1).append(codeSpan);
        }

```

* 坚持
* 不要脸
* 坚持, 不要脸

正文正文正文正文正文正文...
哈哈哈

<input type="text" name="xx" />

&lt;input type=&quot;text&quot; name=&quot;xx&quot; /&gt;

<a href="http://www.baidu.com">jiushizhe</a>

