--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: insert_to_events(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insert_to_events() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
   counter INTEGER := 0 ;
   eventdata text;
begin
WHILE counter <= 1000 LOOP
 counter := counter + 1 ; 
 select event_data from events_4 limit 1 into eventdata;
 insert into events(node_type,event_data,event_type) values('TMDAS',eventdata,'7');
 --insert into events(node_type,event_type) values('TMDAS','7');
 END LOOP ;
 return counter; 
end;
$$;


ALTER FUNCTION public.insert_to_events() OWNER TO postgres;

--
-- Name: inserttestevent(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION inserttestevent() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
   counter INTEGER := 0 ;

begin
 WHILE counter <= 100 LOOP
 counter := counter + 1 ; 
 insert into events(node_type,event_data,event_type) select node_type,event_data,event_type from events_3;
 END LOOP ; 
return counter;
end;
$$;


ALTER FUNCTION public.inserttestevent() OWNER TO postgres;

--
-- Name: keep_unique_events(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION keep_unique_events() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
delete from events where node_type = new.node_type and event_date = new.event_date;
RETURN new;
END;
$$;


ALTER FUNCTION public.keep_unique_events() OWNER TO postgres;

--
-- Name: move_alarm_to_history_table(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION move_alarm_to_history_table() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

insert into alarms_history select * from alarms where ip = new.ip;
delete from alarms where ip = new.ip;
RETURN new;
END;
$$;


ALTER FUNCTION public.move_alarm_to_history_table() OWNER TO postgres;

--
-- Name: move_bms_status_to_history_table(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION move_bms_status_to_history_table() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
insert into bmsstatus_history select * from bmsstatus where ip = new.ip;
delete from bmsstatus where ip = new.ip;
RETURN new;
END;
$$;


ALTER FUNCTION public.move_bms_status_to_history_table() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: aduack; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE aduack (
    id integer NOT NULL,
    logtime timestamp without time zone DEFAULT now(),
    response character varying
);


ALTER TABLE aduack OWNER TO postgres;

--
-- Name: aduack_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aduack_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aduack_id_seq OWNER TO postgres;

--
-- Name: aduack_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE aduack_id_seq OWNED BY aduack.id;


--
-- Name: alarms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE alarms (
    id integer NOT NULL,
    ip character varying,
    component_id numeric,
    componen_type character varying,
    mangedoject_id numeric,
    managedobject_type character varying,
    event_id numeric,
    event_type character varying,
    severity numeric,
    event_desctiption text,
    generation_time timestamp without time zone,
    acknowledged boolean DEFAULT false,
    logtime timestamp without time zone DEFAULT now()
);


ALTER TABLE alarms OWNER TO postgres;

--
-- Name: alarms_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE alarms_history (
    id integer,
    ip character varying,
    component_id numeric,
    componen_type character varying,
    mangedoject_id numeric,
    managedobject_type character varying,
    event_id numeric,
    event_type character varying,
    severity numeric,
    event_desctiption text,
    generation_time timestamp without time zone,
    acknowledged boolean,
    logtime timestamp without time zone
);


ALTER TABLE alarms_history OWNER TO postgres;

--
-- Name: alarms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE alarms_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE alarms_id_seq OWNER TO postgres;

--
-- Name: alarms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE alarms_id_seq OWNED BY alarms.id;


--
-- Name: bmsconfig; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bmsconfig (
    id integer NOT NULL,
    ip character varying,
    name character varying,
    tag character varying,
    value character varying
);


ALTER TABLE bmsconfig OWNER TO postgres;

--
-- Name: bmsconfig_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bmsconfig_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bmsconfig_id_seq OWNER TO postgres;

--
-- Name: bmsconfig_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bmsconfig_id_seq OWNED BY bmsconfig.id;


--
-- Name: bmsstatus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bmsstatus (
    id integer NOT NULL,
    ip character varying,
    bcv1 numeric,
    bcv2 numeric,
    bcv3 numeric,
    bcv4 numeric,
    bcv5 numeric,
    bcv6 numeric,
    bcv7 numeric,
    bcv8 numeric,
    bcv9 numeric,
    bcv10 numeric,
    bcv11 numeric,
    bcv12 numeric,
    bcv13 numeric,
    bcv14 numeric,
    btv numeric,
    tbc numeric,
    soc numeric,
    btemp numeric,
    alarmword numeric,
    logtime timestamp without time zone DEFAULT now()
);


ALTER TABLE bmsstatus OWNER TO postgres;

--
-- Name: bmsstatus_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bmsstatus_history (
    id integer,
    ip character varying,
    bcv1 numeric,
    bcv2 numeric,
    bcv3 numeric,
    bcv4 numeric,
    bcv5 numeric,
    bcv6 numeric,
    bcv7 numeric,
    bcv8 numeric,
    bcv9 numeric,
    bcv10 numeric,
    bcv11 numeric,
    bcv12 numeric,
    bcv13 numeric,
    bcv14 numeric,
    btv numeric,
    tbc numeric,
    soc numeric,
    btemp numeric,
    alarmword numeric,
    logtime timestamp without time zone
);


ALTER TABLE bmsstatus_history OWNER TO postgres;

--
-- Name: bmsstatus_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bmsstatus_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bmsstatus_id_seq OWNER TO postgres;

--
-- Name: bmsstatus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bmsstatus_id_seq OWNED BY bmsstatus.id;


--
-- Name: cell; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cell (
    id integer NOT NULL,
    operator character varying,
    country character varying,
    longitude character varying,
    latitude character varying,
    mcc character varying,
    mnc character varying,
    lac character varying,
    cell character varying,
    rssi numeric,
    antena_id character varying
);


ALTER TABLE cell OWNER TO postgres;

--
-- Name: cell_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cell_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cell_id_seq OWNER TO postgres;

--
-- Name: cell_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cell_id_seq OWNED BY cell.id;


--
-- Name: commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE commands (
    cmd_id integer NOT NULL,
    node_id numeric,
    cmd_tag character varying,
    cmd character varying
);


ALTER TABLE commands OWNER TO postgres;

--
-- Name: commands_cmd_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE commands_cmd_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE commands_cmd_id_seq OWNER TO postgres;

--
-- Name: commands_cmd_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE commands_cmd_id_seq OWNED BY commands.cmd_id;


--
-- Name: cue; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cue (
    id integer NOT NULL,
    reported_time timestamp without time zone,
    reported_by character varying,
    source character varying,
    detail text,
    logtime timestamp without time zone DEFAULT now(),
    cue_id character varying,
    node_type character varying
);


ALTER TABLE cue OWNER TO postgres;

--
-- Name: cue_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cue_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cue_id_seq OWNER TO postgres;

--
-- Name: cue_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cue_id_seq OWNED BY cue.id;


--
-- Name: device_commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE device_commands (
    id integer NOT NULL,
    cmd_name character varying,
    cmd character varying
);


ALTER TABLE device_commands OWNER TO postgres;

--
-- Name: device_commands_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE device_commands_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE device_commands_id_seq OWNER TO postgres;

--
-- Name: device_commands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE device_commands_id_seq OWNED BY device_commands.id;


--
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE events (
    event_id integer NOT NULL,
    event_date timestamp without time zone,
    node_type character varying,
    event_data text,
    event_type character varying,
    acknowledged boolean DEFAULT false
);


ALTER TABLE events OWNER TO postgres;

--
-- Name: events_event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE events_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE events_event_id_seq OWNER TO postgres;

--
-- Name: events_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE events_event_id_seq OWNED BY events.event_id;


--
-- Name: map_rotation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE map_rotation (
    id integer NOT NULL,
    user_id integer,
    angle integer
);


ALTER TABLE map_rotation OWNER TO postgres;

--
-- Name: map_rotation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE map_rotation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE map_rotation_id_seq OWNER TO postgres;

--
-- Name: map_rotation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE map_rotation_id_seq OWNED BY map_rotation.id;


--
-- Name: nodes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE nodes (
    node_id integer NOT NULL,
    node_name character varying,
    node_ip character varying,
    status character varying,
    url character varying,
    status_update_type character varying DEFAULT 'ping'::character varying,
    active boolean DEFAULT true,
    display_name character varying,
    node_icon character varying,
    login_path character varying
);


ALTER TABLE nodes OWNER TO postgres;

--
-- Name: nodes_node_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE nodes_node_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nodes_node_id_seq OWNER TO postgres;

--
-- Name: nodes_node_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE nodes_node_id_seq OWNED BY nodes.node_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    user_id integer NOT NULL,
    user_name character varying,
    password character varying,
    role character varying,
    enabled boolean DEFAULT true
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.user_id;


--
-- Name: aduack id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aduack ALTER COLUMN id SET DEFAULT nextval('aduack_id_seq'::regclass);


--
-- Name: alarms id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY alarms ALTER COLUMN id SET DEFAULT nextval('alarms_id_seq'::regclass);


--
-- Name: bmsconfig id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bmsconfig ALTER COLUMN id SET DEFAULT nextval('bmsconfig_id_seq'::regclass);


--
-- Name: bmsstatus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bmsstatus ALTER COLUMN id SET DEFAULT nextval('bmsstatus_id_seq'::regclass);


--
-- Name: cell id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cell ALTER COLUMN id SET DEFAULT nextval('cell_id_seq'::regclass);


--
-- Name: commands cmd_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY commands ALTER COLUMN cmd_id SET DEFAULT nextval('commands_cmd_id_seq'::regclass);


--
-- Name: cue id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cue ALTER COLUMN id SET DEFAULT nextval('cue_id_seq'::regclass);


--
-- Name: device_commands id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_commands ALTER COLUMN id SET DEFAULT nextval('device_commands_id_seq'::regclass);


--
-- Name: events event_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events ALTER COLUMN event_id SET DEFAULT nextval('events_event_id_seq'::regclass);


--
-- Name: map_rotation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY map_rotation ALTER COLUMN id SET DEFAULT nextval('map_rotation_id_seq'::regclass);


--
-- Name: nodes node_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY nodes ALTER COLUMN node_id SET DEFAULT nextval('nodes_node_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN user_id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: aduack; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aduack (id, logtime, response) FROM stdin;
\.


--
-- Data for Name: alarms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alarms (id, ip, component_id, componen_type, mangedoject_id, managedobject_type, event_id, event_type, severity, event_desctiption, generation_time, acknowledged, logtime) FROM stdin;
\.


--
-- Data for Name: alarms_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alarms_history (id, ip, component_id, componen_type, mangedoject_id, managedobject_type, event_id, event_type, severity, event_desctiption, generation_time, acknowledged, logtime) FROM stdin;
\.


--
-- Data for Name: bmsconfig; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bmsconfig (id, ip, name, tag, value) FROM stdin;
\.


--
-- Data for Name: bmsstatus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bmsstatus (id, ip, bcv1, bcv2, bcv3, bcv4, bcv5, bcv6, bcv7, bcv8, bcv9, bcv10, bcv11, bcv12, bcv13, bcv14, btv, tbc, soc, btemp, alarmword, logtime) FROM stdin;
\.


--
-- Data for Name: bmsstatus_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bmsstatus_history (id, ip, bcv1, bcv2, bcv3, bcv4, bcv5, bcv6, bcv7, bcv8, bcv9, bcv10, bcv11, bcv12, bcv13, bcv14, btv, tbc, soc, btemp, alarmword, logtime) FROM stdin;
\.


--
-- Data for Name: cell; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cell (id, operator, country, longitude, latitude, mcc, mnc, lac, cell, rssi, antena_id) FROM stdin;
\.


--
-- Data for Name: commands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY commands (cmd_id, node_id, cmd_tag, cmd) FROM stdin;
3	2	ADU_ON	led 1 on
4	2	ADU_OFF	led 1 off
6	3	ADU_OFF	led 2 off
1	1	ADU_ON	led 3 on
2	1	ADU_OFF	led 3 off
5	3	ADU_ON	led 4 on
\.


--
-- Data for Name: cue; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cue (id, reported_time, reported_by, source, detail, logtime, cue_id, node_type) FROM stdin;
\.


--
-- Data for Name: device_commands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY device_commands (id, cmd_name, cmd) FROM stdin;
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY events (event_id, event_date, node_type, event_data, event_type, acknowledged) FROM stdin;
\.


--
-- Data for Name: map_rotation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY map_rotation (id, user_id, angle) FROM stdin;
\.


--
-- Data for Name: nodes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY nodes (node_id, node_name, node_ip, status, url, status_update_type, active, display_name, node_icon, login_path) FROM stdin;
3	TMDAS	192.168.5.2	0	:8080/locator	http	t	Falcon	Marker-F1.png	:8080/locator
2	TRGL	192.168.4.2	0	:9000	http	t	Hummer	Marker-H1.png	:9000/login
4	ADU	192.168.8.3	0	2013	ping	t	ADU	ADU_NORMAL.png	\N
1	UGS	192.168.6.2	0	:8080	ping	t	Oxfam	Marker-O1.png	:8080/C2-Web
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (user_id, user_name, password, role, enabled) FROM stdin;
1	admin	admin	ROLE_ADMIN	t
2	user	user	ROLE_USER	t
\.


--
-- Name: aduack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aduack_id_seq', 104, true);


--
-- Name: alarms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('alarms_id_seq', 8, true);


--
-- Name: bmsconfig_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bmsconfig_id_seq', 1, false);


--
-- Name: bmsstatus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bmsstatus_id_seq', 2, true);


--
-- Name: cell_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cell_id_seq', 52462, true);


--
-- Name: commands_cmd_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('commands_cmd_id_seq', 6, true);


--
-- Name: cue_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cue_id_seq', 3720, true);


--
-- Name: device_commands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('device_commands_id_seq', 1, false);


--
-- Name: events_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('events_event_id_seq', 988330, true);


--
-- Name: map_rotation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('map_rotation_id_seq', 1, true);


--
-- Name: nodes_node_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('nodes_node_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 2, true);


--
-- Name: aduack aduack_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aduack
    ADD CONSTRAINT aduack_pkey PRIMARY KEY (id);


--
-- Name: alarms alarms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY alarms
    ADD CONSTRAINT alarms_pkey PRIMARY KEY (id);


--
-- Name: bmsconfig bmsconfig_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bmsconfig
    ADD CONSTRAINT bmsconfig_pkey PRIMARY KEY (id);


--
-- Name: bmsstatus bmsstatus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bmsstatus
    ADD CONSTRAINT bmsstatus_pkey PRIMARY KEY (id);


--
-- Name: cell cell_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cell
    ADD CONSTRAINT cell_pkey PRIMARY KEY (id);


--
-- Name: commands commands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY commands
    ADD CONSTRAINT commands_pkey PRIMARY KEY (cmd_id);


--
-- Name: cue cue_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cue
    ADD CONSTRAINT cue_pkey PRIMARY KEY (id);


--
-- Name: device_commands device_commands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_commands
    ADD CONSTRAINT device_commands_pkey PRIMARY KEY (id);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);


--
-- Name: map_rotation map_rotation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY map_rotation
    ADD CONSTRAINT map_rotation_pkey PRIMARY KEY (id);


--
-- Name: nodes nodes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY nodes
    ADD CONSTRAINT nodes_pkey PRIMARY KEY (node_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: alarms move_alarm_to_history; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER move_alarm_to_history BEFORE INSERT ON alarms FOR EACH ROW EXECUTE PROCEDURE move_alarm_to_history_table();


--
-- Name: bmsstatus move_bms_status_to_history; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER move_bms_status_to_history BEFORE INSERT ON bmsstatus FOR EACH ROW EXECUTE PROCEDURE move_bms_status_to_history_table();


--
-- Name: events update_events_table; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_events_table BEFORE INSERT ON events FOR EACH ROW EXECUTE PROCEDURE keep_unique_events();


--
-- PostgreSQL database dump complete
--

