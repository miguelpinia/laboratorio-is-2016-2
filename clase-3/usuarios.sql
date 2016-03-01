--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: miguel; Tablespace:
--

CREATE TABLE usuario (
    id serial NOT NULL,
    apellidos text,
    fecha_registro date,
    nombres text
);


ALTER TABLE public.usuario OWNER TO miguel;

--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: miguel
--

COPY usuario (id, apellidos, fecha_registro, nombres) FROM stdin;
0	Piña	2016-02-14	Miguel
1	Perez	2016-02-21	Juan
\.


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: miguel; Tablespace:
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
