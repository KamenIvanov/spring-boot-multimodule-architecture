-- Create OUTBOX_EVENTS table
CREATE TABLE IF NOT EXISTS `OUTBOX_EVENTS`
(
    `id`           BINARY(16)   NOT NULL,
    `created_at`   DATETIME(6)  NOT NULL         DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`   DATETIME(6),

    -- Event fields
    `aggregate_id` BINARY(16)   NOT NULL,
    `bucket_id`    INT          NOT NULL,
    `event_type`   VARCHAR(255) NOT NULL,
    `topic`        VARCHAR(255) NOT NULL,
    `payload`      JSON         NOT NULL,

    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `IDX_OUTBOX_BUCKET_ID_ORDER` ON `OUTBOX_EVENTS` (`bucket_id`, `id` ASC);
CREATE INDEX `IDX_OUTBOX_EVENTS_AGGREGATE_ID` ON `OUTBOX_EVENTS` (`aggregate_id`);
