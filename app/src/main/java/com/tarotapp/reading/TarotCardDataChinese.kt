package com.tarotapp.reading

import android.content.Context

// Китайские данные карт
object ChineseTarotCardDataLoader : TarotCardDataLoader {
    override fun getTarotCards(context: Context): List<TarotCard> = chineseTarotCards
}

val chineseTarotCards = listOf(
    // 大阿卡纳 (22张牌)
    TarotCard(
        name = "愚者",
        description = "新开始、纯真和自发性的牌。愚者象征着天真、自由和冒险的意愿。这是一张纯粹潜力和无限可能的牌。",
        situation = "你的生活中正在开启新的篇章，充满机遇。现在是时候相信你的直觉并自发行动。不要害怕冒险，走出舒适区。",
        imagePath = "cards/00.jpg",
        keywords = listOf("新开始", "自发性", "自由", "纯真", "冒险"),
        reversedMeaning = "鲁莽、不成熟、危险行为、无法从错误中学习",
        advice = "相信你的直觉，对新可能性保持开放。不要担心未来，活在当下。",
        element = "风",
        planet = "天王星"
    ),
    TarotCard(
        name = "魔术师",
        description = "魔术师代表意志力、掌握和将想法转化为现实的能力。这是一张积极行动、自信和使用所有可用资源的牌。",
        situation = "你拥有实现目标所需的所有工具和机会。现在是积极行动和展现才能的时候。你拥有成功的力量和能力。",
        imagePath = "cards/01.jpg",
        keywords = listOf("掌握", "行动", "意志力", "资源", "自信"),
        reversedMeaning = "操纵、不诚实、无法行动、错失机会",
        advice = "明智地使用你的技能和资源。自信地行动，但不要忘记诚实和道德。",
        element = "火",
        planet = "水星"
    ),
    TarotCard(
        name = "女祭司",
        description = "女祭司象征着秘密知识、直觉和内在智慧。这是一张深刻理解、神秘启示和与潜意识连接的牌。",
        situation = "是时候倾听你的直觉和内在声音了。不要急于做决定，让情况自然展开。相信你的智慧。",
        imagePath = "cards/02.jpg",
        keywords = listOf("直觉", "智慧", "秘密", "被动", "内在声音"),
        reversedMeaning = "隐藏动机、不信任直觉、肤浅、无法自我分析",
        advice = "花时间冥想和自我反思。相信你的直觉，不要急于求成。",
        element = "水",
        planet = "月亮"
    ),
    TarotCard(
        name = "女皇",
        description = "女皇体现丰盛、创造力、母性和生育能力。这是一张繁荣、和谐和生活中所有领域蓬勃发展的牌。",
        situation = "繁荣和发展的时期。你的创造能力处于巅峰，关系和谐，物质福祉正在增长。这是一个丰盛和实现潜力的时期。",
        imagePath = "cards/03.jpg",
        keywords = listOf("丰盛", "创造力", "生育", "和谐", "繁荣"),
        reversedMeaning = "依赖、过度保护、创作危机、财务问题",
        advice = "允许自己创造和建设。照顾自己和亲人，但不要忘记个人界限。",
        element = "土",
        planet = "金星"
    ),
    TarotCard(
        name = "皇帝",
        description = "皇帝象征着权力、结构、稳定和领导力。这是一张秩序、纪律和通过组织实现目标能力的牌。",
        situation = "是时候在你的生活中建立秩序和结构了。你的领导素质很受欢迎，你可以通过组织和纪律取得成功。",
        imagePath = "cards/04.jpg",
        keywords = listOf("权力", "结构", "稳定", "领导力", "组织"),
        reversedMeaning = "暴政、僵化、无法妥协、失去控制",
        advice = "展现你的领导素质，但记住权力和灵活性之间的平衡。建立明确的界限和规则。",
        element = "火",
        planet = "火星"
    ),
    TarotCard(
        name = "教皇",
        description = "传统、学习、信仰。",
        situation = "遵循规则，寻求智者的建议。",
        imagePath = "cards/05.jpg",
        keywords = listOf("传统", "学习", "信仰", "灵性", "指导"),
        reversedMeaning = "非传统、不信仰、拒绝权威、灵性危机",
        advice = "倾听明智导师的建议，但不要忘记自己的经验和直觉。",
        element = "土",
        planet = "木星"
    ),
    TarotCard(
        name = "恋人",
        description = "选择、爱情、平衡。",
        situation = "重要决定，关系和谐。",
        imagePath = "cards/06.jpg",
        keywords = listOf("选择", "爱情", "平衡", "和谐", "决策"),
        reversedMeaning = "不和谐、错误选择、冲突、无法做决定",
        advice = "跟随你的心，但不要忘记理性。有意识地做决定。",
        element = "风",
        planet = "金星"
    ),
    TarotCard(
        name = "战车",
        description = "决心、向前进。",
        situation = "坚定行动，你将达到目标。",
        imagePath = "cards/07.jpg",
        keywords = listOf("决心", "运动", "目标", "胜利", "控制"),
        reversedMeaning = "失去控制、障碍、延迟、内部冲突",
        advice = "自信地朝着目标前进，但不要忘记平衡和和谐。",
        element = "水",
        planet = "火星"
    ),
    TarotCard(
        name = "力量",
        description = "内在能量、勇气。",
        situation = "展现耐心和内在力量。",
        imagePath = "cards/08.jpg",
        keywords = listOf("力量", "勇气", "耐心", "内在能量", "勇敢"),
        reversedMeaning = "虚弱、不安全感、失去控制、内在恐惧",
        advice = "明智地使用你的内在力量。耐心和温柔往往比蛮力更有效。",
        element = "火",
        planet = "太阳"
    ),
    TarotCard(
        name = "隐士",
        description = "自我分析、独处。",
        situation = "思考你的行动，相信内在声音。",
        imagePath = "cards/09.jpg",
        keywords = listOf("自我分析", "独处", "智慧", "内在探索", "反思"),
        reversedMeaning = "孤独、孤立、失去视角、过度封闭",
        advice = "为自我分析和反思找时间，但不要忘记与世界的联系。",
        element = "土",
        planet = "水星"
    ),
    TarotCard(
        name = "命运之轮",
        description = "命运、变化、循环。",
        situation = "不可预测但重要的变化。",
        imagePath = "cards/10.jpg",
        keywords = listOf("命运", "变化", "循环", "运气", "转变"),
        reversedMeaning = "不幸、停滞、抗拒变化、坏运气",
        advice = "接受变化作为生活的一部分。准备好适应新环境。",
        element = "火",
        planet = "木星"
    ),
    TarotCard(
        name = "正义",
        description = "平衡、诚实、真理。",
        situation = "解决争议，公平结果。",
        imagePath = "cards/11.jpg",
        keywords = listOf("正义", "平衡", "诚实", "真理", "法律"),
        reversedMeaning = "不公正、不平衡、不诚实、偏见",
        advice = "诚实公正地行动。基于事实而非情感做决定。",
        element = "风",
        planet = "金星"
    ),
    TarotCard(
        name = "倒吊人",
        description = "牺牲、新视角。",
        situation = "接受情况，在其中找到教训。",
        imagePath = "cards/12.jpg",
        keywords = listOf("牺牲", "新视角", "暂停", "重新评估", "等待"),
        reversedMeaning = "延迟、抗拒、无法行动、失去视角",
        advice = "有时需要停下来从不同角度看待情况。不要急于求成。",
        element = "水",
        planet = "海王星"
    ),
    TarotCard(
        name = "死神",
        description = "结束、转变、新阶段。",
        situation = "放下旧的，开始新的。",
        imagePath = "cards/13.jpg",
        keywords = listOf("转变", "变化", "新阶段", "更新", "过渡"),
        reversedMeaning = "抗拒变化、停滞、害怕新事物、无法前进",
        advice = "接受不可避免的变化。有时需要放下旧的才能为新事物开路。",
        element = "水",
        planet = "冥王星"
    ),
    TarotCard(
        name = "节制",
        description = "和谐、平衡、耐心。",
        situation = "找到中间道路，保持平静。",
        imagePath = "cards/14.jpg",
        keywords = listOf("节制", "和谐", "平衡", "耐心", "平衡"),
        reversedMeaning = "不平衡、极端、不耐烦、失去平衡",
        advice = "在所有事情中寻求黄金中庸。保持平静和耐心。",
        element = "火",
        planet = "太阳"
    ),
    TarotCard(
        name = "恶魔",
        description = "执着、限制、诱惑。",
        situation = "摆脱有害习惯或依赖。",
        imagePath = "cards/15.jpg",
        keywords = listOf("执着", "限制", "诱惑", "依赖", "物质主义"),
        reversedMeaning = "解放、克服依赖、摆脱限制",
        advice = "认识你的依赖和限制。努力克服它们。",
        element = "土",
        planet = "土星"
    ),
    TarotCard(
        name = "高塔",
        description = "危机、摧毁旧的。",
        situation = "突然变化，开启新机会。",
        imagePath = "cards/16.jpg",
        keywords = listOf("危机", "摧毁", "变化", "冲击", "解放"),
        reversedMeaning = "避免危机、渐进变化、抗拒摧毁",
        advice = "接受不可避免的变化。有时摧毁旧的是创造新事物的必要条件。",
        element = "火",
        planet = "火星"
    ),
    TarotCard(
        name = "星星",
        description = "希望、灵感、治愈。",
        situation = "相信你的梦想，前方有光明的未来。",
        imagePath = "cards/17.jpg",
        keywords = listOf("希望", "灵感", "治愈", "信仰", "乐观"),
        reversedMeaning = "失望、失去信仰、悲观、绝望",
        advice = "保持希望和对美好的信仰。与他人分享你的光芒。",
        element = "风",
        planet = "天王星"
    ),
    TarotCard(
        name = "月亮",
        description = "幻觉、恐惧、直觉。",
        situation = "当心欺骗，相信你的感觉。",
        imagePath = "cards/18.jpg",
        keywords = listOf("幻觉", "恐惧", "直觉", "神秘", "潜意识"),
        reversedMeaning = "揭露幻觉、克服恐惧、清晰",
        advice = "倾听你的直觉，但要检查事实。不要让恐惧控制你。",
        element = "水",
        planet = "月亮"
    ),
    TarotCard(
        name = "太阳",
        description = "喜悦、成功、清晰。",
        situation = "幸运和幸福在你这边。",
        imagePath = "cards/19.jpg",
        keywords = listOf("喜悦", "成功", "清晰", "幸福", "乐观"),
        reversedMeaning = "暂时困难、成功延迟、过度乐观",
        advice = "享受生活并与他人分享你的喜悦。明智地利用成功时期。",
        element = "火",
        planet = "太阳"
    ),
    TarotCard(
        name = "审判",
        description = "重生、觉醒、过渡。",
        situation = "新阶段，重大决定。",
        imagePath = "cards/20.jpg",
        keywords = listOf("重生", "觉醒", "过渡", "召唤", "觉醒"),
        reversedMeaning = "怀疑、延迟、无法做决定、害怕变化",
        advice = "倾听你的内在声音。是时候做重要决定的时候了。",
        element = "火",
        planet = "冥王星"
    ),
    TarotCard(
        name = "世界",
        description = "完成、完整、成功。",
        situation = "完成重要事务，满足。",
        imagePath = "cards/21.jpg",
        keywords = listOf("完成", "完整", "成功", "成就", "和谐"),
        reversedMeaning = "不完整、延迟、不满足、缺乏完整",
        advice = "庆祝你的成就。利用成功时期开始新事物。",
        element = "土",
        planet = "土星"
    ),
    // 小阿卡纳牌可以在这里添加...
) 