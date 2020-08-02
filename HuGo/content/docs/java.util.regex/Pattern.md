# Java 正则表达式



## 边界

|    Regex    | Desc             | Demo |
| :---------: | ---------------- | ---- |
|     `^`     | 行头             |      |
|     `$`     | 行尾             |      |
| `\b` 、`\B` | 单词边界、非边界 |      |

## 字符

|    Regex     | Desc                                              | Demo |
| :----------: | ------------------------------------------------- | ---- |
|     `.`      | 任意字符                                          |      |
| `\d` 、 `\D` | 数字 `[0-9]`、非数字 `[^0-9]`， **Digit**         |      |
| `\s` 、 `\S` | 空白`[ \t\n\x0B\f\r]`、非空白 `[^\s]`， **Space** |      |
| `\w`、 `\W`  | 单词、非单词                                      |      |

## 数量

|  Regex   | Desc                      | Demo |
| :------: | ------------------------- | ---- |
|   `X?`   | `0~1`                     |      |
|   `X*`   | `0~n`                     |      |
|   `X+`   | `1~n`                     |      |
|  `X{n}`  | 正好 `n`次                |      |
| `X{n,}`  | 至少 `n`次                |      |
| `X{n,m}` | 至少 `n`次，不超过 `m` 次 |      |

## 零宽断言

- **断言**：
  - 俗话的断言就是 **我断定什么什么**
  - 正则中的断言是 **在指定的内容的前面或后面会出现满足指定规则的内容**
  - **只有当断言为真时才会继续进行匹配**
- **零宽**：就是没有宽度，只是匹配位置，不占字符，也就是说，**匹配结果里是不会返回断言本身**
- **负向零宽断言**
  - 断言用来断定 **是什么**
  - 负向断言用来断定 **不是什么**

| 位置 |        |      断言       | Demo |
| :--: | :----: | :-------------: | ---- |
|  前  |  符合  | `(?<=exp)` *regex* | `(?<=\bre)\w+\b` 匹配 `reading a book` 出 `ading` |
|  后  |  符合  | *regex* `(?=exp)` | `\b\w+(?=ing\b)`匹配 `you're dancing` 出 `danc` |
|  前  | 不符合 |   `(?<!exp)` regex   | `(?<![a-z])\d{7}` 匹配前面不是小写字母的七位数字 |
|  后  | 不符合 |    *regex* `(?!exp)`    | `\d{3}(?!\d)` 匹配三位数字，但是后面不能是数字 |
|  |  | `(?:exp)` | 括号内的表达式，不作为一个 Group，并不属于零宽断言的范畴 |


> **如何记忆？**
>
> - 断言的语法结构是 `(?...)`
> - `<` 搭配 html 的起始标签理解`<exp>regex</exp>`，写在正则前，**断言正则前必须符合指定的条件**
> - `=` 必须符合断言，`!` 必须不符合断言



## 匹配模式

|        |      Pattern       | Desc                 |
| :----: | :----------------: | -------------------- |
| `(?m)` |    `MULTILINE`     | 多行模式             |
| `(?i)` | `CASE_INSENSITIVE` | 不缺分大小写         |
| `(?x)` |     `COMMENTS`     | 允许使用注释         |
| `(?s)` |      `DOTALL`      | `.` 可以匹配行结束符 |
|   ..   |         ..         | ..                   |



## Demo

### 隐藏手机后中间四位

```java
// $1 引用 前3位，$2 引用 后4位
"13843838438".replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3")
// (?:\\d{4}) 代表不作为一个 group 
"13843838438".replaceAll("(\\d{3})(?:\\d{4})(\\d{4})", "$1****$2")
// 同上
"13843838438".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")

// 或
new StringBuilder("13843838438").replace(3,7,"****")
```

### replace & replaceAll

都能替换 全部匹配的字符

```java
// 字符串匹配
public String replace(CharSequence target, CharSequence replacement)

// 正则匹配
public String replaceAll(String regex, String replacement)
  
// 替换正则匹配到的第一个
public String replaceFirst(String regex, String replacement)
```



## Read More

- [java.util.regex 类 Pattern](https://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/regex/Pattern.html)
- [正则表达式30分钟入门教程](https://deerchao.cn/tutorials/regex/regex.htm)

---

- [在线正则表达式测试](https://tool.oschina.net/regex/)