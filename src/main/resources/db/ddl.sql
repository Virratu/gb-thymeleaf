CREATE TABLE PRODUCT
(
    ID                 BIGSERIAL      NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255)   NOT NULL,
    COST               DECIMAL(10, 2) NOT NULL,
    MANUFACTURE_DATE   DATE           NOT NULL,
    VERSION            INT            NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',

    UNIQUE (TITLE)
);

DROP TABLE PRODUCT;

SELECT * FROM PRODUCT;

update product set status = 'ACTIVE' where id = 91

-- Drop table

-- DROP TABLE public.cart;

CREATE TABLE public.cart (
	username varchar NOT NULL,
	id bigserial NOT NULL,
	CONSTRAINT cart_pk PRIMARY KEY (id)
);

-- Drop table

-- DROP TABLE public.cart_product;

CREATE TABLE public.cart_product (
	cart_id int8 NOT NULL,
	product_id int8 NOT NULL,
	CONSTRAINT cart_product_fk FOREIGN KEY (cart_id) REFERENCES public.cart(id),
	CONSTRAINT cart_product_fk_1 FOREIGN KEY (product_id) REFERENCES public.product(id)
);
