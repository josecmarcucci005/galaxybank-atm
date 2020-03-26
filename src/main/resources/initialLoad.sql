insert into bank (bank_id, address, name, swift_code) values (1, 'bla bla', 'Citi Bank', 'CITIXXXXXX')
ON CONFLICT (bank_id) DO NOTHING;

insert into atm (atm_id, location, bank_id) values (1, 'London Bridge', 1)
ON CONFLICT (atm_id) DO NOTHING;

insert into customer (customer_id, address, name, phone, bank_id) values (1, 'Mount Everest', 'John Smith', '456465', 1)
ON CONFLICT (customer_id) DO NOTHING;

insert into account (account_id, account_type, balance, customer_id) values (12345, 'CURRENT', '1000', 1)
ON CONFLICT (account_id) DO NOTHING;

insert into card (card_id, expiring_date, security_cd, account_id) values (123456789, '2025-02-14', '1234', 12345)
ON CONFLICT (card_id) DO NOTHING;

--insert into transaction (transaction_id, amount, transaction_date, transaction_type, atm_id, card_id) values (1, 1000, '2020-02-14', 'DEPOSIT', 1, 123456789);

--insert into account_cards values (12345, 123456789);

--insert into customer_accounts values (1, 12345);

--insert into card_transactions values (123456789, 1);

--insert into bank_atm_list values (1, 1);

--insert into bank_customers values (1, 1);
