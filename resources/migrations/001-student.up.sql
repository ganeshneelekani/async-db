CREATE TABLE students (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  date_of_birth DATE NOT NULL,
  enrollment_date DATE NOT NULL,
  graduation_date DATE,
  major VARCHAR(255)
);
