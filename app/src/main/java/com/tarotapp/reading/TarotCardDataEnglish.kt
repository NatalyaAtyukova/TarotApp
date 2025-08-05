package com.tarotapp.reading

import android.content.Context

// Английские данные карт
object EnglishTarotCardDataLoader : TarotCardDataLoader {
    override fun getTarotCards(context: Context): List<TarotCard> = englishTarotCards
}

val englishTarotCards = listOf(
    // Major Arcana (22 cards)
    TarotCard(
        name = "The Fool",
        description = "A card of new beginnings, purity, and spontaneity. The Fool symbolizes innocence, freedom, and readiness for adventure. This is a card of pure potential and unlimited possibilities.",
        situation = "A new chapter is opening in your life, full of opportunities. This is a time to trust your intuition and act spontaneously. Don't be afraid to take risks and step outside your comfort zone.",
        imagePath = "cards/00.jpg",
        keywords = listOf("new beginnings", "spontaneity", "freedom", "innocence", "adventure"),
        reversedMeaning = "Recklessness, immaturity, risky behavior, inability to learn from mistakes",
        advice = "Trust your intuition and be open to new possibilities. Don't worry about the future, live in the present moment.",
        element = "Air",
        planet = "Uranus"
    ),
    TarotCard(
        name = "The Magician",
        description = "The Magician represents willpower, mastery, and the ability to manifest ideas into reality. This is a card of active action, self-confidence, and using all available resources.",
        situation = "You have all the necessary tools and opportunities to achieve your goals. This is a time for active action and expressing your talents. You possess the strength and abilities for success.",
        imagePath = "cards/01.jpg",
        keywords = listOf("mastery", "action", "willpower", "resources", "confidence"),
        reversedMeaning = "Manipulation, dishonesty, inability to act, missed opportunities",
        advice = "Use your skills and resources wisely. Act confidently, but don't forget about honesty and ethics.",
        element = "Fire",
        planet = "Mercury"
    ),
    TarotCard(
        name = "The High Priestess",
        description = "The High Priestess symbolizes secret knowledge, intuition, and inner wisdom. This is a card of deep understanding, mystical revelations, and connection with the subconscious.",
        situation = "It's time to listen to your intuition and inner voice. Don't rush decisions, let the situation unfold naturally. Trust your wisdom.",
        imagePath = "cards/02.jpg",
        keywords = listOf("intuition", "wisdom", "secrets", "passivity", "inner voice"),
        reversedMeaning = "Hidden motives, distrust of intuition, superficiality, inability to self-analyze",
        advice = "Spend time in meditation and self-reflection. Trust your intuition and don't rush events.",
        element = "Water",
        planet = "Moon"
    ),
    TarotCard(
        name = "The Empress",
        description = "The Empress embodies abundance, creativity, motherhood, and fertility. This is a card of prosperity, harmony, and flourishing in all areas of life.",
        situation = "A time of flourishing and prosperity. Your creative abilities are at their peak, relationships are harmonious, and material well-being is growing. This is a period of abundance and realizing potential.",
        imagePath = "cards/03.jpg",
        keywords = listOf("abundance", "creativity", "fertility", "harmony", "prosperity"),
        reversedMeaning = "Dependency, overprotection, creative crisis, financial problems",
        advice = "Allow yourself to create and build. Take care of yourself and your loved ones, but don't forget about personal boundaries.",
        element = "Earth",
        planet = "Venus"
    ),
    TarotCard(
        name = "The Emperor",
        description = "The Emperor symbolizes power, structure, stability, and leadership. This is a card of order, discipline, and the ability to achieve goals through organization.",
        situation = "Time to establish order and structure in your life. Your leadership qualities are in demand, and you can achieve success through organization and discipline.",
        imagePath = "cards/04.jpg",
        keywords = listOf("power", "structure", "stability", "leadership", "organization"),
        reversedMeaning = "Tyranny, rigidity, inability to compromise, loss of control",
        advice = "Show your leadership qualities, but remember the balance between power and flexibility. Establish clear boundaries and rules.",
        element = "Fire",
        planet = "Mars"
    ),
    // Добавьте остальные карты здесь...
    TarotCard(
        name = "The Hierophant",
        description = "Traditions, learning, faith.",
        situation = "Follow the rules, seek advice from a sage.",
        imagePath = "cards/05.jpg",
        keywords = listOf("traditions", "learning", "faith", "spirituality", "mentorship"),
        reversedMeaning = "Untraditional, disbelief, rejection of authorities, spiritual crisis",
        advice = "Listen to the advice of wise mentors, but don't forget about your own experience and intuition.",
        element = "Earth",
        planet = "Jupiter"
    ),
    TarotCard(
        name = "The Lovers",
        description = "Choice, love, balance.",
        situation = "Important decision, harmony in relationships.",
        imagePath = "cards/06.jpg",
        keywords = listOf("choice", "love", "balance", "harmony", "decision making"),
        reversedMeaning = "Disharmony, wrong choice, conflicts, inability to make decisions",
        advice = "Follow your heart, but don't forget about reason. Make decisions consciously.",
        element = "Air",
        planet = "Venus"
    ),
    // Продолжите с остальными картами...
    TarotCard(
        name = "The Chariot",
        description = "Determination, moving forward.",
        situation = "Act with determination, you will achieve your goal.",
        imagePath = "cards/07.jpg",
        keywords = listOf("determination", "movement", "goal", "victory", "control"),
        reversedMeaning = "Loss of control, obstacles, delays, internal conflicts",
        advice = "Move toward your goal with confidence, but don't forget about balance and harmony.",
        element = "Water",
        planet = "Mars"
    ),
    // Добавьте остальные карты по аналогии...
    TarotCard(
        name = "Strength",
        description = "Inner energy, courage.",
        situation = "Show patience and inner strength.",
        imagePath = "cards/08.jpg",
        keywords = listOf("strength", "courage", "patience", "inner energy", "bravery"),
        reversedMeaning = "Weakness, insecurity, loss of control, inner fears",
        advice = "Use your inner strength wisely. Patience and gentleness are often more effective than brute force.",
        element = "Fire",
        planet = "Sun"
    ),
    // Продолжите с остальными картами...
    TarotCard(
        name = "The Hermit",
        description = "Self-analysis, solitude.",
        situation = "Think about your actions, trust your inner voice.",
        imagePath = "cards/09.jpg",
        keywords = listOf("self-analysis", "solitude", "wisdom", "inner search", "reflection"),
        reversedMeaning = "Loneliness, isolation, loss of perspective, excessive withdrawal",
        advice = "Find time for self-analysis and reflection, but don't forget about connection with the world.",
        element = "Earth",
        planet = "Mercury"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "Wheel of Fortune",
        description = "Fate, changes, cycle.",
        situation = "Unpredictable but important changes.",
        imagePath = "cards/10.jpg",
        keywords = listOf("fate", "changes", "cycle", "luck", "transformation"),
        reversedMeaning = "Misfortune, stagnation, resistance to change, bad luck",
        advice = "Accept changes as part of life. Be ready to adapt to new circumstances.",
        element = "Fire",
        planet = "Jupiter"
    ),
    // Продолжите с остальными картами...
    TarotCard(
        name = "Justice",
        description = "Balance, honesty, truth.",
        situation = "Resolving disputes, fair outcome.",
        imagePath = "cards/11.jpg",
        keywords = listOf("justice", "balance", "honesty", "truth", "law"),
        reversedMeaning = "Injustice, imbalance, dishonesty, bias",
        advice = "Act honestly and fairly. Make decisions based on facts, not emotions.",
        element = "Air",
        planet = "Venus"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The Hanged Man",
        description = "Sacrifice, new perspective.",
        situation = "Accept the situation, find a lesson in it.",
        imagePath = "cards/12.jpg",
        keywords = listOf("sacrifice", "new perspective", "pause", "reassessment", "waiting"),
        reversedMeaning = "Delays, resistance, inability to act, loss of perspective",
        advice = "Sometimes you need to stop and look at the situation from a different angle. Don't rush events.",
        element = "Water",
        planet = "Neptune"
    ),
    // Продолжите с остальными картами...
    TarotCard(
        name = "Death",
        description = "End, transformation, new stage.",
        situation = "Letting go of the old, beginning of the new.",
        imagePath = "cards/13.jpg",
        keywords = listOf("transformation", "change", "new stage", "renewal", "transition"),
        reversedMeaning = "Resistance to change, stagnation, fear of the new, inability to move forward",
        advice = "Accept inevitable changes. Sometimes you need to let go of the old to open the way for the new.",
        element = "Water",
        planet = "Pluto"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "Temperance",
        description = "Harmony, balance, patience.",
        situation = "Find the middle path, maintain calm.",
        imagePath = "cards/14.jpg",
        keywords = listOf("temperance", "harmony", "balance", "patience", "equilibrium"),
        reversedMeaning = "Imbalance, extremes, impatience, loss of equilibrium",
        advice = "Seek the golden mean in everything. Maintain calm and patience.",
        element = "Fire",
        planet = "Sun"
    ),
    // Продолжите с остальными картами...
    TarotCard(
        name = "The Devil",
        description = "Attachments, limitations, temptations.",
        situation = "Get rid of harmful habits or dependencies.",
        imagePath = "cards/15.jpg",
        keywords = listOf("attachments", "limitations", "temptations", "dependencies", "materialism"),
        reversedMeaning = "Liberation, overcoming dependencies, getting rid of limitations",
        advice = "Recognize your dependencies and limitations. Work on overcoming them.",
        element = "Earth",
        planet = "Saturn"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The Tower",
        description = "Crisis, destruction of the old.",
        situation = "Sudden changes that open new opportunities.",
        imagePath = "cards/16.jpg",
        keywords = listOf("crisis", "destruction", "change", "shock", "liberation"),
        reversedMeaning = "Avoiding crisis, gradual changes, resistance to destruction",
        advice = "Accept inevitable changes. Sometimes destruction of the old is necessary to create the new.",
        element = "Fire",
        planet = "Mars"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The Star",
        description = "Hope, inspiration, healing.",
        situation = "Believe in your dreams, bright future ahead.",
        imagePath = "cards/17.jpg",
        keywords = listOf("hope", "inspiration", "healing", "faith", "optimism"),
        reversedMeaning = "Disappointment, loss of faith, pessimism, despair",
        advice = "Maintain hope and faith in the best. Share your light with others.",
        element = "Air",
        planet = "Uranus"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The Moon",
        description = "Illusions, fears, intuition.",
        situation = "Beware of deception, trust your feelings.",
        imagePath = "cards/18.jpg",
        keywords = listOf("illusions", "fears", "intuition", "mysteries", "subconscious"),
        reversedMeaning = "Exposure of illusions, overcoming fears, clarity",
        advice = "Listen to your intuition, but check the facts. Don't let fears control you.",
        element = "Water",
        planet = "Moon"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The Sun",
        description = "Joy, success, clarity.",
        situation = "Luck and happiness are on your side.",
        imagePath = "cards/19.jpg",
        keywords = listOf("joy", "success", "clarity", "happiness", "optimism"),
        reversedMeaning = "Temporary difficulties, delays in success, excessive optimism",
        advice = "Rejoice in life and share your joy with others. Use the period of success wisely.",
        element = "Fire",
        planet = "Sun"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "Judgement",
        description = "Rebirth, awareness, transition.",
        situation = "New stage, cardinal decision.",
        imagePath = "cards/20.jpg",
        keywords = listOf("rebirth", "awareness", "transition", "calling", "awakening"),
        reversedMeaning = "Doubt, delay, inability to make decisions, fear of change",
        advice = "Listen to your inner voice. It's time for important decisions.",
        element = "Fire",
        planet = "Pluto"
    ),
    // Добавьте остальные карты...
    TarotCard(
        name = "The World",
        description = "Completion, wholeness, success.",
        situation = "Completion of an important matter, satisfaction.",
        imagePath = "cards/21.jpg",
        keywords = listOf("completion", "wholeness", "success", "achievement", "harmony"),
        reversedMeaning = "Incompleteness, delays, dissatisfaction, lack of wholeness",
        advice = "Celebrate your achievements. Use the period of success for new beginnings.",
        element = "Earth",
        planet = "Saturn"
    ),
    // Добавьте остальные карты для младших арканов...
    // Это базовая структура, которую можно расширить
) 