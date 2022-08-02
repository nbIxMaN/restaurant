CREATE TABLE IF NOT EXISTS "checks" (
    id SERIAL NOT NULL,
    date timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "positions" (
    id SERIAL NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS "positions_index" ON "positions" (name);

CREATE TABLE IF NOT EXISTS "suppliers" (
    id SERIAL NOT NULL,
    name varchar(255) UNIQUE,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS "suppliers_index" ON "suppliers" (name);

CREATE TABLE IF NOT EXISTS "ingredients" (
    id SERIAL NOT NULL,
    name varchar(255) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "dish_categories" (
    id SERIAL NOT NULL,
    name varchar(255) UNIQUE,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS "dish_categories_index" ON "dish_categories" (name);

CREATE TABLE IF NOT EXISTS "dishes" (
    id SERIAL NOT NULL,
    name varchar(255),
    price INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS "dishes_index" ON "dishes" (name);

CREATE TABLE IF NOT EXISTS "employees" (
    id SERIAL NOT NULL,
    position_id INTEGER NOT NULL,
    name varchar(255),
    salary DECIMAL NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "employees_position_id" FOREIGN KEY (position_id) REFERENCES "positions"(id)
);

CREATE TABLE IF NOT EXISTS "orders" (
    id SERIAL NOT NULL,
    dish_id INTEGER NOT NULL,
    check_id INTEGER,
    order_date timestamp,
    preparation_date timestamp,
    service_date timestamp,
    PRIMARY KEY (id),
    CONSTRAINT "orders_dish_id" FOREIGN KEY (dish_id) REFERENCES "dishes"(id),
    CONSTRAINT "orders_check_id" FOREIGN KEY (check_id) REFERENCES "checks"(id)
);

CREATE TABLE IF NOT EXISTS "orders2employees" (
    id SERIAL NOT NULL,
    order_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "orders2employees_order_id" FOREIGN KEY (order_id) REFERENCES "orders"(id),
    CONSTRAINT "orders2employees_employee_id" FOREIGN KEY (employee_id) REFERENCES "employees"(id)
);

CREATE TABLE IF NOT EXISTS "suppliers2ingredients" (
    id SERIAL NOT NULL,
    ingredient_id INTEGER NOT NULL,
    supplier_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "suppliers2ingredients_ingredient_id" FOREIGN KEY (ingredient_id) REFERENCES "ingredients"(id),
    CONSTRAINT "suppliers2ingredients_supplier_id" FOREIGN KEY (supplier_id) REFERENCES "suppliers"(id)
);

CREATE TABLE IF NOT EXISTS "dishes2ingredients" (
    id SERIAL NOT NULL,
    ingredient_id INTEGER NOT NULL,
    dish_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "dishes2ingredients_ingredient_id" FOREIGN KEY (ingredient_id) REFERENCES "ingredients"(id),
    CONSTRAINT "dishes2ingredients_dish_id" FOREIGN KEY (dish_id) REFERENCES "dishes"(id)
);

CREATE TABLE IF NOT EXISTS "dishes2dish_categories" (
    id SERIAL NOT NULL,
    dish_category_id INTEGER NOT NULL,
    dish_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "dishes2dish_categories_dish_category_id" FOREIGN KEY (dish_category_id) REFERENCES "dish_categories"(id),
    CONSTRAINT "dishes2dish_categories_dish_id" FOREIGN KEY (dish_id) REFERENCES "dishes"(id)
);