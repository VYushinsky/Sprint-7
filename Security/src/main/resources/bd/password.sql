DROP TABLE IF EXISTS password;

CREATE TABLE password (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL
);

INSERT INTO password (first_name, last_name, email) VALUES
                                                             ('Lokesh', 'Gupta', 'abc@gmail.com'),
                                                             ('Deja', 'Vu', 'xyz@email.com'),
                                                             ('Caption', 'America', 'cap@marvel.com');