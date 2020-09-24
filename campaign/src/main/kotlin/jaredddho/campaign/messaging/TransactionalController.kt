package jaredddho.campaign.messaging

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
        value = ["/txn"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
class TransactionalController(
        private val transactionManager: TransactionManager
) {

    @RequestMapping(
            value = ["/{templateId}"],
            method = [RequestMethod.POST]
    )
    fun triggerTransactionalMessage(
            @PathVariable("templateId")
            templateId: String,
            @RequestBody
            transactionalPayload: TransactionalPayload
    ): TransactionalMessageResponse = transactionManager
            .triggerTransactionalEvent(
                    templateId,
                    transactionalPayload
            )
}