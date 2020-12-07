

CREATE TABLE `ali_pay_configuration` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `isv_app_id` varchar(255) DEFAULT NULL,
    `isv_uid` varchar(255) DEFAULT NULL,
    `sign_type` varchar(255) DEFAULT NULL,
    `gateway` varchar(255) DEFAULT NULL,
    `private_key` varchar(255) DEFAULT NULL,
    `public_key` varchar(255) DEFAULT NULL,
    `royalty_uid` varchar(255) DEFAULT NULL,
    `isv_number` varchar(255) DEFAULT NULL,
    `fee_back_uid` varchar(255) DEFAULT NULL,
    `status` int(11) DEFAULT NULL,
    `seller_no` varchar(255) DEFAULT NULL,
    `format` varchar(255) DEFAULT NULL,
    `charset` varchar(255) DEFAULT NULL,
    `service_provider_id` varchar(255) DEFAULT NULL,
    `app_id` varchar(255) DEFAULT NULL,
    `ip_id` varchar(255) DEFAULT NULL,
    `role_id` varchar(255) DEFAULT NULL,
    `my_bank_app_id` varchar(255) DEFAULT NULL,
    `my_bank_public_key` varchar(255) DEFAULT NULL,
    `my_bank_private_key` varchar(255) DEFAULT NULL,
    `my_bank_uid` varchar(255) DEFAULT NULL,
    `my_bank_seller_no` varchar(255) DEFAULT NULL,
    `bill_date` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

