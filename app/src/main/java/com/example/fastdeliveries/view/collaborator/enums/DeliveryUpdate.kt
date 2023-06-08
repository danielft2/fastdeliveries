package com.example.fastdeliveries.view.collaborator.enums

enum class DeliveryUpdate {
    SAINDO_PARA_ENTREGA {
        override fun toString(): String {
            return "Objeto saiu para entrga."
        } },

    MOVIMENTANDO_ENTRE_AS_FILIAIS {
        override fun toString(): String {
            return "0 objeto está em movimento entre as filiais."
        } },

    EM_MOVIMENTO {
        override fun toString(): String {
            return "0 objeto está em movimento."
        } },

    CHEGOU_NO_LOCAL_DE_DESTINO {
        override fun toString(): String {
            return "0 objeto chehou a cidade de destino."
        } },

    AGUARDANDO_MOVIMENTACAO {
        override fun toString(): String {
            return "Aguardando movimentação."
        } }
}