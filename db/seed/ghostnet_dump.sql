--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-09-20 19:07:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16421)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16400)
-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_user (
    id uuid NOT NULL,
    email character varying(255) NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255) NOT NULL,
    phonenumber character varying(255)
);


ALTER TABLE public.app_user OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16394)
-- Name: ghost_net; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ghost_net (
    id uuid NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    status character varying(255),
    assigned_user_id uuid,
    reported_by_user_id uuid,
    estimated_size character varying(255),
    CONSTRAINT ghostnets_status_check CHECK (((status)::text = ANY ((ARRAY['GEMELDET'::character varying, 'BERGUNG_BEVORSTEHEND'::character varying, 'GEBORGEN'::character varying, 'VERSCHOLLEN'::character varying])::text[])))
);


ALTER TABLE public.ghost_net OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 16400)
-- Dependencies: 219
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.app_user (id, email, firstname, lastname, password, phonenumber) FROM stdin;
1dcfbe1a-da50-4387-89c0-79735f25c796	max@mustermann.de	Max	Mustermann	passwort1	123456
d75ec8c7-8e20-4314-915e-3ceeabfc79ac	maria@neumann.de	Maria	Neumann	passwort2	56789
dd4255de-3411-4d2f-96f4-e85a3e2320e7	anna.meier@example.com	Anna	Meier	geheim123	0171-1234567
3e1157f0-1bcf-4683-9114-f05ac15b40d3	lukas.schmitt@example.com	Lukas	Schmitt	netzretter	0157-9876543
2c653f1c-dc58-4adc-9827-4c398cc25734	sophie.mueller@example.com	Sophie	Müller	testpass	0160-5554443
1e9eacf2-983b-4a29-a45e-58dc95296d2e	jan.becker@example.com	Jan	Becker	sicher123	0176-1112233
ab75a5da-9bfa-4430-a38b-99bb680d67bd	lea.schneider@example.com	Lea	Schneider	bergung2025	0172-7890123
\.


--
-- TOC entry 4936 (class 0 OID 16394)
-- Dependencies: 218
-- Data for Name: ghost_net; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ghost_net (id, latitude, longitude, status, assigned_user_id, reported_by_user_id, estimated_size) FROM stdin;
191d324c-32b1-49e3-bc97-9f3b1526e761	54.5	13.4	GEMELDET	\N	1dcfbe1a-da50-4387-89c0-79735f25c796	ca. 10 m²
4c6e1f7b-dc1c-4204-992a-e83e234c10b1	54.8	13.2	GEMELDET	\N	1e9eacf2-983b-4a29-a45e-58dc95296d2e	2x3 Meter
0b276dac-d3cf-446d-a533-74fc1fa9e680	55.1	12.8	GEMELDET	\N	2c653f1c-dc58-4adc-9827-4c398cc25734	riesiges Netz über 20 m Länge
1bfee47d-8d2e-4e96-b92a-521e20e56c62	54.6	13.6	GEMELDET	\N	d75ec8c7-8e20-4314-915e-3ceeabfc79ac	geschätzte 50 m²
9e6cac06-4371-457d-888b-91b52b797ac1	54.7	13.5	GEMELDET	\N	dd4255de-3411-4d2f-96f4-e85a3e2320e7	ca. 15 x 4 m
df222df6-fcc7-4ac0-843f-6092a92d6e7e	54.2	13.1	GEMELDET	\N	\N	Netzteil an Felsen verfangen
11f35807-cc4f-498d-9c19-7fb33ba375fc	54.8	13.1	GEMELDET	\N	1e9eacf2-983b-4a29-a45e-58dc95296d2e	2 m breite Netzbahn
469f25a7-4f4b-4bc8-9951-6c964b33fc4c	55	13.4	GEMELDET	\N	\N	Netzreste mit Boje
31af17b8-f923-41ca-ab7a-dab7c122fa2e	54.4	13.7	GEMELDET	\N	\N	schwimmendes Netzstück
ba15c8d6-3c8e-46a9-ba83-de58d6f38fed	55.2	13	GEMELDET	\N	d75ec8c7-8e20-4314-915e-3ceeabfc79ac	ca. 5 m Durchmesser
dcfd6548-96d0-4769-ab37-2c5d830f2840	54.9	13	GEMELDET	\N	\N	klein, aber tiefhängend
ea527cb6-1075-49a8-b09f-4fc4d90332e6	54.6	13.8	GEMELDET	\N	1dcfbe1a-da50-4387-89c0-79735f25c796	20 m², sehr dicht
431567ec-0fb9-4302-b623-ee2722d2e810	55	13.1	VERSCHOLLEN	\N	\N	Bündel Netzreste
ab48d6d1-f96a-490c-a8d5-c709bcb9fef1	54.1	13.9	BERGUNG_BEVORSTEHEND	1dcfbe1a-da50-4387-89c0-79735f25c796	dd4255de-3411-4d2f-96f4-e85a3e2320e7	ca. 30 m² (geschätzt)
1c523f1a-d3af-4a4d-aefd-82a7122249eb	54.3	13.3	GEBORGEN	1dcfbe1a-da50-4387-89c0-79735f25c796	2c653f1c-dc58-4adc-9827-4c398cc25734	großes Schleppnetzfragment
7c208506-f772-4e95-80d3-d5f7357811d5	55.69	17.07	VERSCHOLLEN	1dcfbe1a-da50-4387-89c0-79735f25c796	d75ec8c7-8e20-4314-915e-3ceeabfc79ac	\N
\.


--
-- TOC entry 4786 (class 2606 OID 16406)
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4784 (class 2606 OID 16399)
-- Name: ghost_net ghostnets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ghost_net
    ADD CONSTRAINT ghostnets_pkey PRIMARY KEY (id);


--
-- TOC entry 4788 (class 2606 OID 16408)
-- Name: app_user uk1j9d9a06i600gd43uu3km82jw; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT uk1j9d9a06i600gd43uu3km82jw UNIQUE (email);


--
-- TOC entry 4789 (class 2606 OID 16409)
-- Name: ghost_net fk9afaobhd22l751mp2or7r1skl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ghost_net
    ADD CONSTRAINT fk9afaobhd22l751mp2or7r1skl FOREIGN KEY (assigned_user_id) REFERENCES public.app_user(id);


--
-- TOC entry 4790 (class 2606 OID 16414)
-- Name: ghost_net fkn5jxi0f3t36d39eooxcp95s4u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ghost_net
    ADD CONSTRAINT fkn5jxi0f3t36d39eooxcp95s4u FOREIGN KEY (reported_by_user_id) REFERENCES public.app_user(id);


-- Completed on 2025-09-20 19:07:00

--
-- PostgreSQL database dump complete
--

