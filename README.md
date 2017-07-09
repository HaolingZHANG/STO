# STO（Special Training in Orienteering）

## 中文版本
### 介绍
　　STO的全称是Special Training in Orienteering，中文名称为定向专项训练科目。该软件所解决的问题为在定向地图缺乏且能够开展定向运动地区的针对较专业运动员培训问题（改变教练只以长跑能力作为定向运动员唯一训练及其选拔标准的倾向）。  
　　STO目前集成三大原创方法，分别为计算式平均跑 、梅花式莱格尔以及图例式莱格尔。  
　　（1）计算式平均跑： 该方法是模拟定向运动员在缺氧运动中的定期定量思维能力。受训者需尽可能以相同的步幅和步频奔跑并进行简单数学题的记背、心算以及选择正确答案。之所以要以相同的步幅和步频奔跑，是因为在定向越野运动中保持良好的行进节奏有利于对周围环境进行有效感知。该方法的技术等级（难度）是依据定向越野地图的难度与复杂度制定的，最低等级对应专业定向运动员在作训或比赛时单次阅读定向地图的平均最大距离，反之，最高等级对应最小距离。目前STO中设定的该方法的参数如下表所示：

|对象|所有值|
|:-----:|:-----:|
|单次距离|100M -> 80M -> 50M -> 40M|
|总跑动距离|800M - 8000M|
|算数方程式|A × B + C|
|A|[0, 9]，随机|
|B|[0, 9]，随机|
|C|[-99, 99]，随机|  

　　（2）梅花式莱格尔： 该方法是模拟定向运动员在百米定向中对方向的判断与记忆能力。专业运动员在进行百米定向的过程中，通常会时刻牢记自己当前位置，将要到达的控制点以及下一个控制点位置。受训者依据震动和提示音开始训练，每一次提示后快速到达下一个控制点。除开第一次会显示受训者当前奔跑路线以外，手机应用中只显示其下一条将跑路线，直到结束。提示间隔以“莱格尔跑”的音乐提示间隔为依据，在训练体能的同时，训练其对方向的记忆与判断。该方法的技术等级是依据单次阅读百米定向地图记忆控制点个数以及无氧训练强度制定的。目前STO中设定的该方法的参数如下表所示：

|对象|所有值|
|:-----:|:-----:|
|梅花阵边长|20M|
|总控制点数|20个 - 100个|
|方向|不重复随机|
|莱格尔初始组数|第11组，第21组，第31组，第41组|

　　（3）图例式莱格尔： 该方法是模拟在缺氧的定向过程中对定向地图的认知能力。在该训练的每一次冲刺中，受训者必须做完屏幕中该应用左上角所示数量的图例选择题。通常，对于初学定向的参与者而言，在运动中对图例符号进行识别的能力过低，建议先在软件设置中记背图例符号。该方法的技术等级是依据单次阅读野外定向地图时识别与分析的图例个数制定的。目前STO中设定的该方法的参数如下表所示：

|对象|所有值|
|:-----:|:-----:|
|单次题目数量|1道 -> 2道 -> 3道 -> 4道|
|总趟数（来+回）|20趟 - 100趟|
|折返长度|20M|

　　目前，该软件仅支持Android手机终端，完全支持中文，美式英语翻译不完善，暂不支持其他语种。 后续方法制作人正在紧张且积极的设计与研发。同时也诚邀不同国籍不同语言的定向粉丝参与设计与研发。此外，该软件虽然为免费软件，但是每一步必须经过严格的审核以及苛刻的调试，以保证能够正确且准确的训练以及教育他人。



## English version（United State）
### Introduction
　The full name of STO is Special Training in Orienteering. The software is used to solve the problem of training and quantitative selection of the entry-level orienteering athletes in a region where lack orienteering map and can be developed (To change the tendency of coach that the only standard in training and selection is long-distance running ability).   
　STO integrates three original methods currently，being called Arithmetic Mean Running (AMR), Bleep Test in Plum-blossom (BTP) and Bleep Test with Legend-symbol (BTL) respectively.  
　（1）AMR：AMR has simulated the skill of orienteering athletes thinking and memory under hypoxia. Trainees should run with same stride length and stride frequency, and remember the numbers, mental arithmetic then choose the right answer. The reason why AMR needs trainees take the same stride length and stride frequency in running is that keeping a good running rhythm in orienteering is easier to effectively perceive the environment.The technical level is based on the difficulty and complexity of orienteering maps. The simplest value and the hardest value correspond to the maximum theoretical distance of each simple sums and the minimum theoretical distance of each simple sums of reading the field orienteering map each time. In this paper, the minimum difficulty and the maximum difficulty of setting parameters are simple sums in 100M and simple sums in 40M.
