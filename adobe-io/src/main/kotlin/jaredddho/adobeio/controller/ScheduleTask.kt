package jaredddho.adobeio.controller

import jaredddho.adobeio.service.TokenManager
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class ScheduleTask(
        private val tokenManager: TokenManager
) : QuartzJobBean() {

    override fun executeInternal(jobExecutionContext: JobExecutionContext) = tokenManager.refreshToken()
}