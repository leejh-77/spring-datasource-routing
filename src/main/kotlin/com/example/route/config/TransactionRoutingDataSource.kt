package com.example.route.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class TransactionRoutingDataSource : AbstractRoutingDataSource() {

    enum class Type {
        Master,
        Replica
    }

    override fun determineCurrentLookupKey(): Type {
        return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            Type.Replica
        } else {
            Type.Master
        }
    }
}
