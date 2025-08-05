package com.tarotapp.reading

import android.content.Context

// Испанские данные карт
object SpanishTarotCardDataLoader : TarotCardDataLoader {
    override fun getTarotCards(context: Context): List<TarotCard> = spanishTarotCards
}

val spanishTarotCards = listOf(
    // Arcanos Mayores (22 cartas)
    TarotCard(
        name = "El Loco",
        description = "Una carta de nuevos comienzos, pureza y espontaneidad. El Loco simboliza la inocencia, la libertad y la disposición para la aventura. Esta es una carta de puro potencial y posibilidades ilimitadas.",
        situation = "Se está abriendo un nuevo capítulo en tu vida, lleno de oportunidades. Es momento de confiar en tu intuición y actuar espontáneamente. No tengas miedo de arriesgarte y salir de tu zona de confort.",
        imagePath = "cards/00.jpg",
        keywords = listOf("nuevos comienzos", "espontaneidad", "libertad", "inocencia", "aventura"),
        reversedMeaning = "Imprudencia, inmadurez, comportamiento arriesgado, incapacidad de aprender de errores",
        advice = "Confía en tu intuición y mantente abierto a nuevas posibilidades. No te preocupes por el futuro, vive el momento presente.",
        element = "Aire",
        planet = "Urano"
    ),
    TarotCard(
        name = "El Mago",
        description = "El Mago representa la fuerza de voluntad, la maestría y la capacidad de manifestar ideas en realidad. Esta es una carta de acción activa, confianza en sí mismo y uso de todos los recursos disponibles.",
        situation = "Tienes todas las herramientas y oportunidades necesarias para alcanzar tus metas. Es momento de acción activa y expresar tus talentos. Posees la fuerza y las habilidades para el éxito.",
        imagePath = "cards/01.jpg",
        keywords = listOf("maestría", "acción", "fuerza de voluntad", "recursos", "confianza"),
        reversedMeaning = "Manipulación, deshonestidad, incapacidad de actuar, oportunidades perdidas",
        advice = "Usa tus habilidades y recursos sabiamente. Actúa con confianza, pero no olvides la honestidad y la ética.",
        element = "Fuego",
        planet = "Mercurio"
    ),
    TarotCard(
        name = "La Sacerdotisa",
        description = "La Sacerdotisa simboliza el conocimiento secreto, la intuición y la sabiduría interior. Esta es una carta de comprensión profunda, revelaciones místicas y conexión con el subconsciente.",
        situation = "Es momento de escuchar tu intuición y tu voz interior. No te apresures con las decisiones, permite que la situación se desarrolle naturalmente. Confía en tu sabiduría.",
        imagePath = "cards/02.jpg",
        keywords = listOf("intuición", "sabiduría", "secretos", "pasividad", "voz interior"),
        reversedMeaning = "Motivos ocultos, desconfianza de la intuición, superficialidad, incapacidad de autoanálisis",
        advice = "Pasa tiempo en meditación y autoanálisis. Confía en tu intuición y no apresures los eventos.",
        element = "Agua",
        planet = "Luna"
    ),
    TarotCard(
        name = "La Emperatriz",
        description = "La Emperatriz encarna la abundancia, la creatividad, la maternidad y la fertilidad. Esta es una carta de prosperidad, armonía y florecimiento en todas las áreas de la vida.",
        situation = "Un tiempo de florecimiento y prosperidad. Tus habilidades creativas están en su apogeo, las relaciones son armoniosas y el bienestar material está creciendo. Este es un período de abundancia y realización del potencial.",
        imagePath = "cards/03.jpg",
        keywords = listOf("abundancia", "creatividad", "fertilidad", "armonía", "prosperidad"),
        reversedMeaning = "Dependencia, sobreprotección, crisis creativa, problemas financieros",
        advice = "Permítete crear y construir. Cuida de ti mismo y de tus seres queridos, pero no olvides los límites personales.",
        element = "Tierra",
        planet = "Venus"
    ),
    TarotCard(
        name = "El Emperador",
        description = "El Emperador simboliza el poder, la estructura, la estabilidad y el liderazgo. Esta es una carta de orden, disciplina y capacidad de lograr metas a través de la organización.",
        situation = "Es momento de establecer orden y estructura en tu vida. Tus cualidades de liderazgo están en demanda, y puedes lograr el éxito a través de la organización y la disciplina.",
        imagePath = "cards/04.jpg",
        keywords = listOf("poder", "estructura", "estabilidad", "liderazgo", "organización"),
        reversedMeaning = "Tiranía, rigidez, incapacidad de compromiso, pérdida de control",
        advice = "Muestra tus cualidades de liderazgo, pero recuerda el equilibrio entre poder y flexibilidad. Establece límites y reglas claras.",
        element = "Fuego",
        planet = "Marte"
    ),
    TarotCard(
        name = "El Hierofante",
        description = "Tradiciones, aprendizaje, fe.",
        situation = "Sigue las reglas, busca consejo de un sabio.",
        imagePath = "cards/05.jpg",
        keywords = listOf("tradiciones", "aprendizaje", "fe", "espiritualidad", "tutoría"),
        reversedMeaning = "No tradicional, incredulidad, rechazo de autoridades, crisis espiritual",
        advice = "Escucha los consejos de mentores sabios, pero no olvides tu propia experiencia e intuición.",
        element = "Tierra",
        planet = "Júpiter"
    ),
    TarotCard(
        name = "Los Enamorados",
        description = "Elección, amor, equilibrio.",
        situation = "Decisión importante, armonía en relaciones.",
        imagePath = "cards/06.jpg",
        keywords = listOf("elección", "amor", "equilibrio", "armonía", "toma de decisiones"),
        reversedMeaning = "Desarmonía, elección incorrecta, conflictos, incapacidad de tomar decisiones",
        advice = "Sigue tu corazón, pero no olvides la razón. Toma decisiones conscientemente.",
        element = "Aire",
        planet = "Venus"
    ),
    TarotCard(
        name = "El Carro",
        description = "Determinación, movimiento hacia adelante.",
        situation = "Actúa con determinación, lograrás tu objetivo.",
        imagePath = "cards/07.jpg",
        keywords = listOf("determinación", "movimiento", "objetivo", "victoria", "control"),
        reversedMeaning = "Pérdida de control, obstáculos, retrasos, conflictos internos",
        advice = "Muévete hacia tu objetivo con confianza, pero no olvides el equilibrio y la armonía.",
        element = "Agua",
        planet = "Marte"
    ),
    TarotCard(
        name = "La Fuerza",
        description = "Energía interior, coraje.",
        situation = "Muestra paciencia y fuerza interior.",
        imagePath = "cards/08.jpg",
        keywords = listOf("fuerza", "coraje", "paciencia", "energía interior", "valentía"),
        reversedMeaning = "Debilidad, inseguridad, pérdida de control, miedos internos",
        advice = "Usa tu fuerza interior sabiamente. La paciencia y la gentileza a menudo son más efectivas que la fuerza bruta.",
        element = "Fuego",
        planet = "Sol"
    ),
    TarotCard(
        name = "El Ermitaño",
        description = "Autoanálisis, soledad.",
        situation = "Reflexiona sobre tus acciones, confía en tu voz interior.",
        imagePath = "cards/09.jpg",
        keywords = listOf("autoanálisis", "soledad", "sabiduría", "búsqueda interior", "reflexión"),
        reversedMeaning = "Soledad, aislamiento, pérdida de perspectiva, retraimiento excesivo",
        advice = "Encuentra tiempo para autoanálisis y reflexión, pero no olvides la conexión con el mundo.",
        element = "Tierra",
        planet = "Mercurio"
    ),
    TarotCard(
        name = "La Rueda de la Fortuna",
        description = "Destino, cambios, ciclo.",
        situation = "Cambios impredecibles pero importantes.",
        imagePath = "cards/10.jpg",
        keywords = listOf("destino", "cambios", "ciclo", "suerte", "transformación"),
        reversedMeaning = "Mala fortuna, estancamiento, resistencia al cambio, mala suerte",
        advice = "Acepta los cambios como parte de la vida. Prepárate para adaptarte a nuevas circunstancias.",
        element = "Fuego",
        planet = "Júpiter"
    ),
    TarotCard(
        name = "La Justicia",
        description = "Equilibrio, honestidad, verdad.",
        situation = "Resolución de disputas, resultado justo.",
        imagePath = "cards/11.jpg",
        keywords = listOf("justicia", "equilibrio", "honestidad", "verdad", "ley"),
        reversedMeaning = "Injusticia, desequilibrio, deshonestidad, prejuicio",
        advice = "Actúa honesta y justamente. Toma decisiones basadas en hechos, no en emociones.",
        element = "Aire",
        planet = "Venus"
    ),
    TarotCard(
        name = "El Colgado",
        description = "Sacrificio, nueva perspectiva.",
        situation = "Acepta la situación, encuentra una lección en ella.",
        imagePath = "cards/12.jpg",
        keywords = listOf("sacrificio", "nueva perspectiva", "pausa", "reevaluación", "espera"),
        reversedMeaning = "Retrasos, resistencia, incapacidad de actuar, pérdida de perspectiva",
        advice = "A veces necesitas detenerte y mirar la situación desde un ángulo diferente. No apresures los eventos.",
        element = "Agua",
        planet = "Neptuno"
    ),
    TarotCard(
        name = "La Muerte",
        description = "Fin, transformación, nueva etapa.",
        situation = "Dejar ir lo viejo, comenzar lo nuevo.",
        imagePath = "cards/13.jpg",
        keywords = listOf("transformación", "cambio", "nueva etapa", "renovación", "transición"),
        reversedMeaning = "Resistencia al cambio, estancamiento, miedo a lo nuevo, incapacidad de avanzar",
        advice = "Acepta los cambios inevitables. A veces necesitas soltar lo viejo para abrir camino a lo nuevo.",
        element = "Agua",
        planet = "Plutón"
    ),
    TarotCard(
        name = "La Templanza",
        description = "Armonía, equilibrio, paciencia.",
        situation = "Encuentra el camino medio, mantén la calma.",
        imagePath = "cards/14.jpg",
        keywords = listOf("templanza", "armonía", "equilibrio", "paciencia", "equilibrio"),
        reversedMeaning = "Desequilibrio, extremos, impaciencia, pérdida de equilibrio",
        advice = "Busca el término medio en todo. Mantén la calma y la paciencia.",
        element = "Fuego",
        planet = "Sol"
    ),
    TarotCard(
        name = "El Diablo",
        description = "Apegos, limitaciones, tentaciones.",
        situation = "Libérate de hábitos dañinos o dependencias.",
        imagePath = "cards/15.jpg",
        keywords = listOf("apegos", "limitaciones", "tentaciones", "dependencias", "materialismo"),
        reversedMeaning = "Liberación, superación de dependencias, liberación de limitaciones",
        advice = "Reconoce tus dependencias y limitaciones. Trabaja para superarlas.",
        element = "Tierra",
        planet = "Saturno"
    ),
    TarotCard(
        name = "La Torre",
        description = "Crisis, destrucción de lo viejo.",
        situation = "Cambios repentinos que abren nuevas oportunidades.",
        imagePath = "cards/16.jpg",
        keywords = listOf("crisis", "destrucción", "cambio", "conmoción", "liberación"),
        reversedMeaning = "Evitar crisis, cambios graduales, resistencia a la destrucción",
        advice = "Acepta los cambios inevitables. A veces la destrucción de lo viejo es necesaria para crear lo nuevo.",
        element = "Fuego",
        planet = "Marte"
    ),
    TarotCard(
        name = "La Estrella",
        description = "Esperanza, inspiración, curación.",
        situation = "Cree en tus sueños, hay un futuro brillante por delante.",
        imagePath = "cards/17.jpg",
        keywords = listOf("esperanza", "inspiración", "curación", "fe", "optimismo"),
        reversedMeaning = "Decepción, pérdida de fe, pesimismo, desesperación",
        advice = "Mantén la esperanza y la fe en lo mejor. Comparte tu luz con otros.",
        element = "Aire",
        planet = "Urano"
    ),
    TarotCard(
        name = "La Luna",
        description = "Ilusiones, miedos, intuición.",
        situation = "Ten cuidado con el engaño, confía en tus sentimientos.",
        imagePath = "cards/18.jpg",
        keywords = listOf("ilusiones", "miedos", "intuición", "misterios", "subconsciente"),
        reversedMeaning = "Exposición de ilusiones, superación de miedos, claridad",
        advice = "Escucha tu intuición, pero verifica los hechos. No dejes que los miedos te controlen.",
        element = "Agua",
        planet = "Luna"
    ),
    TarotCard(
        name = "El Sol",
        description = "Alegría, éxito, claridad.",
        situation = "La suerte y la felicidad están de tu lado.",
        imagePath = "cards/19.jpg",
        keywords = listOf("alegría", "éxito", "claridad", "felicidad", "optimismo"),
        reversedMeaning = "Dificultades temporales, retrasos en el éxito, optimismo excesivo",
        advice = "Disfruta la vida y comparte tu alegría con otros. Usa sabiamente el período de éxito.",
        element = "Fuego",
        planet = "Sol"
    ),
    TarotCard(
        name = "El Juicio",
        description = "Renacimiento, conciencia, transición.",
        situation = "Nueva etapa, decisión cardinal.",
        imagePath = "cards/20.jpg",
        keywords = listOf("renacimiento", "conciencia", "transición", "llamado", "despertar"),
        reversedMeaning = "Duda, demora, incapacidad de tomar decisiones, miedo al cambio",
        advice = "Escucha tu voz interior. Es momento de tomar decisiones importantes.",
        element = "Fuego",
        planet = "Plutón"
    ),
    TarotCard(
        name = "El Mundo",
        description = "Finalización, totalidad, éxito.",
        situation = "Finalización de un asunto importante, satisfacción.",
        imagePath = "cards/21.jpg",
        keywords = listOf("finalización", "totalidad", "éxito", "logro", "armonía"),
        reversedMeaning = "Incompletitud, retrasos, insatisfacción, falta de totalidad",
        advice = "Celebra tus logros. Usa el período de éxito para nuevos comienzos.",
        element = "Tierra",
        planet = "Saturno"
    ),
    // Los Arcanos Menores se pueden agregar aquí...
) 