ALTER TABLE atm ADD COLUMN IF NOT EXISTS balance real default 100000;

insert into bank (bank_id, address, name, swift_code) values (1, 'bla bla', 'Citi Bank', 'CITIXXXXXX')
ON CONFLICT (bank_id) DO NOTHING;

insert into atm (atm_id, location, bank_id, balance) values (1, 'London Bridge', 1, 10000)
ON CONFLICT (atm_id) DO NOTHING;

insert into customer (customer_id, address, name, phone, bank_id) values (1, 'Mount Everest', 'John Smith', '456465', 1)
ON CONFLICT (customer_id) DO NOTHING;

insert into account (account_id, account_type, balance, customer_id) values (12345, 'CURRENT', '1000', 1)
ON CONFLICT (account_id) DO NOTHING;

insert into card (card_id, expiring_date, security_cd, account_id) values (123456789, '2025-02-14', '1234', 12345)
ON CONFLICT (card_id) DO NOTHING;

insert into admin_user (admin_user_id, name, surname, username, email, password) values (1, 'William', 'Baker', '', 'will.baker@gmail.com', 'willbak@123')
ON CONFLICT (admin_user_id) DO NOTHING;
