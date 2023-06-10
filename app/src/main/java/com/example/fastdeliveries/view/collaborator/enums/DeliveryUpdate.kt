package com.example.fastdeliveries.view.collaborator.enums

enum class DeliveryUpdate(val value: String) {
    AGUARDANDO_MOVIMENTACAO("Aguardando movimentação."),
    MOVIMENTANDO_ENTRE_AS_FILIAIS("Movimentando entre as filiais.") ,
    SAINDO_PARA_ENTREGA("Saindo para entrega,"),
    EM_MOVIMENTO("Em movimento."),
    CHEGOU_NO_CIDADE_DE_DESTINO("Na cidade de destino."),
    ENTREGUE_AO_DESTINATARIO("Entrege ao destinatário.")
}