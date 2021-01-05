CREATE TABLE "person"
(
"person_id" serial PRIMARY KEY NOT NULL,
"age" int NOT NULL,
"first_name" varchar(100) NOT NULL,
"last_name" varchar(100) NOT NULL,
"person_number" varchar(20) UNIQUE NOT NULL,
"street" varchar(100) NOT NULL,
"zip" varchar(20) NOT NULL,
"city" varchar(100) NOT NULL
);

CREATE TABLE "phone_number"
(
"person_id" int REFERENCES "person" ON DELETE CASCADE,
"phone_number" varchar(100)NOT NULL,
PRIMARY KEY("person_id", "phone_number")
);

CREATE TABLE "email"
(
"person_id" int PRIMARY KEY NOT NULL REFERENCES "person" ON DELETE CASCADE,
"email" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "student"
(
"student_id" serial PRIMARY KEY NOT NULL,
"person_id" int NOT NULL REFERENCES "person"
);

CREATE TABLE "siblings"
(
"student_id" int NOT NULL REFERENCES "student" ON DELETE CASCADE,
"id_of_student_sibling" int NOT NULL,
PRIMARY KEY("student_id", "id_of_student_sibling")
);

CREATE TABLE "instructor_application"
(
"instructor_application_id" serial PRIMARY KEY NOT NULL,
"person_id" int NOT NULL REFERENCES "person",
"keep_application_status" boolean NOT NULL
);

CREATE TABLE "instructor"
(
"instructor_id" serial PRIMARY KEY NOT NULL,
"instructor_application_id" int NOT NULL REFERENCES "instructor_application",
"person_id" int NOT NULL REFERENCES "person",
"ensemble_trained" boolean NOT NULL
);

CREATE TABLE "instructor_daily_work_schedule"
(
"instructor_id" int  NOT NULL REFERENCES "instructor",
"date" date NOT NULL,
"start_of_shift" timestamp NOT NULL,
"end_of_shift" timestamp NOT NULL,
PRIMARY KEY("instructor_id", "date")
);

CREATE TABLE "teachable_instrument"
(
"instructor_id" int NOT NULL REFERENCES "instructor" ON DELETE CASCADE,
"instrument_name" varchar(100) NOT NULL,
PRIMARY KEY("instructor_id", "instrument_name")
);

CREATE TABLE "lesson_type"
(
"lesson_type_id" serial PRIMARY KEY NOT NULL, 
"lesson_type" varchar(100) NOT NULL
);

CREATE TABLE "audition"
(
"audition_id" serial PRIMARY KEY NOT NULL,
"person_id" int NOT NULL REFERENCES "person" ON DELETE CASCADE,
"lesson_type_id" int NOT NULL REFERENCES "lesson_type" ON DELETE CASCADE,
"audition_passed" boolean,
"date" date NOT NULL,
"start_time" timestamp NOT NULL,
"end_time" timestamp NOT NULL
);

CREATE TABLE "lesson_type_enrollment"
(
"person_id" int NOT NULL REFERENCES "person" ON DELETE CASCADE,
"lesson_type_id" int NOT NULL REFERENCES "lesson_type" ON DELETE CASCADE,
"offered_position" boolean,
"person_accepted" boolean,
PRIMARY KEY("person_id", "lesson_type_id")
);

CREATE TABLE "student_application"
(
"student_application_id" serial PRIMARY KEY NOT NULL, 
"person_id" int NOT NULL REFERENCES "person",
"lesson_type_id" int NOT NULL REFERENCES "lesson_type",
"instrument_name" varchar(100) NOT NULL,
"instrument_skill" varchar(100) NOT NULL,
"keep_application" boolean NOT NULL
);

CREATE TABLE "booked_session"
(
"booked_session_id" serial PRIMARY KEY NOT NULL,
"date" date NOT NULL,
"start_of_session" timestamp NOT NULL,
"end_of_session" timestamp NOT NULL,
"session_carried_out" boolean,
"day_dependent_extra_payment" varchar(10)
"instructor_id" int NOT NULL REFERENCES "instructor"

);

CREATE TABLE "student_booked_session"
(
"student_id" int NULL REFERENCES "student",
"booked_session_id" int NOT NULL REFERENCES "booked_session",
PRIMARY KEY("booked_session_id", "student_id")
);

CREATE TABLE "guardian_email"
(
"student_id" int NOT NULL REFERENCES "student" ON DELETE CASCADE,
"guardian_email" varchar(100) NOT NULL,
PRIMARY KEY("guardian_email", "student_id")
);

CREATE TABLE "guardian_phone_number"
(
"student_id" int NOT NULL REFERENCES "student" ON DELETE CASCADE,
"guardian_phone_number" varchar(100) NOT NULL,
PRIMARY KEY("guardian_phone_number", "student_id")
);

CREATE TABLE "instrument_record"
(
"instrument_record_id" serial PRIMARY KEY NOT NULL,
"instrument_type" varchar(100),
"instrument_name" varchar(100) NOT NULL,
"rented_status" boolean NOT NULL,
"monthly_payment" varchar(10) NOT NULL,
"brand" varchar(100)
);

CREATE TABLE "student_rent_record"
(
"student_rent_record_id" serial PRIMARY KEY NOT NULL, 
"instrument_record_id" int NOT NULL REFERENCES "instrument_record",
"student_id" int NOT NULL REFERENCES "student",
"date_of_rent" date NOT NULL ,
"end_of_rent" date ,
"last_date_of_lease" date NOT NULL
);

CREATE TABLE "instrument_lesson"
(
"instrument_lesson_id" serial PRIMARY KEY NOT NULL,
"lesson_type_id" int NOT NULL REFERENCES "lesson_type" ON DELETE CASCADE,
"instrument_name" varchar(100) NOT NULL,
"instrument_skill" varchar(100) NOT NULL,
"max_number_of_students_in_lesson_type" int NOT NULL,
"price_for_individual_lessons" varchar(10) NOT NULL,
"price_for_group_lessons" varchar(10) NOT NULL
);

CREATE TABLE "group_instrument_lesson"
(
"group_instrument_lesson_id" serial PRIMARY KEY NOT NULL,
"instrument_lesson_id" int NOT NULL REFERENCES "instrument_lesson" ON DELETE CASCADE,
"week_day" varchar(30) NOT NULL,
"min_number_of_students" int NOT NULL,
"max_number_of_students" int NOT NULL
);

CREATE TABLE "individual_instrument_lesson"
(
"individual_instrument_lesson_id" serial PRIMARY KEY NOT NULL,
"instrument_lesson_id" int NOT NULL REFERENCES "instrument_lesson" ON DELETE CASCADE,
"booked_session_id" int NOT NULL REFERENCES "booked_session" ON DELETE CASCADE
);

CREATE TABLE "ensemble"
(
"ensemble_id" serial PRIMARY KEY NOT NULL,
"lesson_type_id" int NOT NULL REFERENCES "lesson_type" ON DELETE CASCADE,
"week_day" varchar(100) NOT NULL,
"music_genre" varchar(100) NOT NULL,
"min_number_of_students_in_ensemble" int NOT NULL,
"max_number_of_students_in_ensemble" int NOT NULL,
"lesson_price" varchar(10) NOT NULL
);

CREATE TABLE "booked_session_group_instrument_lesson"
(
"booked_lesson_id" int NOT NULL REFERENCES "booked_session" ON DELETE CASCADE,
"group_instrument_lesson_id" int NOT NULL REFERENCES "group_instrument_lesson" ON DELETE CASCADE
);

CREATE TABLE "booked_session_ensemble"
(
"booked_lesson_id" int NOT NULL REFERENCES "booked_session" ON DELETE CASCADE,
"ensemble_id" int NOT NULL REFERENCES "ensemble" ON DELETE CASCADE
);