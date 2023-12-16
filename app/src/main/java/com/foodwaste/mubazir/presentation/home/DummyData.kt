package com.foodwaste.mubazir.presentation.home

data class Recommendation(
    val category: Int,
    val title: String,
    val distance: Int,
    val price: Int,
    val imageUrl: String
)

var dummyData = listOf(
    Recommendation(
        category = 2,
        title = "Bolu Pukis Budi Agung",
        distance = 560,
        price = 5000,
        imageUrl = "https://static.promediateknologi.id/crop/20x185:718x625/750x500/webp/photo/2023/08/07/kue-pukis-4258304942.jpg"
    ),
    Recommendation(
        category = 1,
        title = "Ayam Geprek Mas Budi",
        distance = 720,
        price = 10000,
        imageUrl = "https://www.nestleprofessional.co.id/sites/default/files/srh_recipes/4845bc09ddd23ca5a27d75c3ea424db3.png"
    ),
    Recommendation(
        category = 3,
        title = "Pepaya mateng",
        distance = 1200,
        price = 15000,
        imageUrl = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2022/05/12/pepayajpg-20220512080939.jpg"
    ),
    Recommendation(
        category = 1,
        title = "Nasi Goreng Ronaldo",
        distance = 1520,
        price = 8500,
        imageUrl = "https://assets-pergikuliner.com/5DQRl4RcX7HdqUFaFIW-rC6_exw=/fit-in/1366x768/smart/filters:no_upscale()/https://assets-pergikuliner.com/uploads/image/picture/2228193/picture-1621505010.jpg"
    ),
)

var dummyData1 = listOf(
    Recommendation(
        category = 1,
        title = "Ayam Geprek Mas Budi",
        distance = 720,
        price = 10000,
        imageUrl = "https://www.nestleprofessional.co.id/sites/default/files/srh_recipes/4845bc09ddd23ca5a27d75c3ea424db3.png"
    ),

    Recommendation(
        category = 1,
        title = "Nasi Goreng Ronaldo",
        distance = 1520,
        price = 8500,
        imageUrl = "https://assets-pergikuliner.com/5DQRl4RcX7HdqUFaFIW-rC6_exw=/fit-in/1366x768/smart/filters:no_upscale()/https://assets-pergikuliner.com/uploads/image/picture/2228193/picture-1621505010.jpg"
    ),

    Recommendation(
        category = 1,
        title = "Donat sweet seventeen",
        distance = 1780,
        price = 3000,
        imageUrl = "https://files.prokerala.com/recipes/pics/1024/homemade-donut-92.jpg"
    ),

    Recommendation(
        category = 1,
        title = "Mie Ayam Apung",
        distance = 2398,
        price = 10000,
        imageUrl = "https://img.kurio.network/8SAPXEhKaUInnM_g0gIyPZtB0hk=/411x231/filters:quality(80)/https://kurio-img.kurioapps.com/21/05/31/538ab368-478d-45bb-a39c-8c968b150bde.jpe"
    ),
)

var dummyData2 = listOf(

    Recommendation(
        category = 2,
        title = "Bolu Pukis Budi Agung",
        distance = 560,
        price = 5000,
        imageUrl = "https://static.promediateknologi.id/crop/20x185:718x625/750x500/webp/photo/2023/08/07/kue-pukis-4258304942.jpg"
    ),

    Recommendation(
        category = 2,
        title = "Nasi kotak tahlilan lebih",
        distance = 3960,
        price = 0,
        imageUrl = "https://img.ws.mms.shopee.co.id/0db38fced0c5d4dedf8a0465691cb60e"
    ),
)

var dummyData3 = listOf(
    Recommendation(
        category = 3,
        title = "Pepaya mateng",
        distance = 1200,
        price = 15000,
        imageUrl = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2022/05/12/pepayajpg-20220512080939.jpg"
    ),

    Recommendation(
        category = 3,
        title = "Pisang ambon mateng",
        distance = 1440,
        price = 10000,
        imageUrl = "https://allofresh.id/blog/wp-content/uploads/2023/04/image-38.png"
    ),

)