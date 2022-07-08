CREATE SEQUENCE IF NOT EXISTS public.deals_id_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.deals_id_sequence
    OWNER TO postgres;


CREATE SEQUENCE IF NOT EXISTS public.users_id_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.users_id_sequence
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL,
    first_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    patronymic character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_sequence'::regclass);

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.deals
(
    id bigint NOT NULL,
    service_city character varying(20) COLLATE pg_catalog."default" NOT NULL,
    service_date timestamp without time zone NOT NULL,
    service_desc character varying(200) COLLATE pg_catalog."default" NOT NULL,
    service_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    service_price numeric(19,2) NOT NULL,
    owner_id bigint,
    CONSTRAINT deals_pkey PRIMARY KEY (id),
    CONSTRAINT fkelgjlc4287c3492tviyvrv52f FOREIGN KEY (owner_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT deals_service_price_check CHECK (service_price >= 0::numeric)
)

TABLESPACE pg_default;

ALTER TABLE deals ALTER COLUMN id SET DEFAULT nextval('deals_id_sequence'::regclass);

ALTER TABLE IF EXISTS public.deals
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.deals_subscribers
(
    deal_id bigint NOT NULL,
    subscribers_id bigint NOT NULL,
    CONSTRAINT deals_subscribers_pkey PRIMARY KEY (deal_id, subscribers_id),
    CONSTRAINT uk_g0s42ql3s412bpp81uuoj1kk7 UNIQUE (subscribers_id),
    CONSTRAINT fkmjeis57buog8n87tnyc9p096s FOREIGN KEY (deal_id)
        REFERENCES public.deals (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmoxfmdefirn57i03i78n4nl3k FOREIGN KEY (subscribers_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.services_subscribers
    OWNER to postgres;
