package jaredddho.adobeio.config

import jaredddho.adobeio.controller.ScheduleTask
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder.newTrigger
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SchedulerConfiguration(
        private val logger: Logger,
        private val adobeIOProperties: AdobeIOProperties
) {

    @Bean
    fun scheduleJob(): JobDetail {
        return JobBuilder.newJob(ScheduleTask::class.java)
                .storeDurably()
                .build()
    }

    @Bean
    fun scheduleTrigger(): Trigger {
        return newTrigger()
                .forJob(scheduleJob())
                .withSchedule(CronScheduleBuilder.cronSchedule(adobeIOProperties.cronSchedule))
                .startNow()
                .build()
    }
}