create table resume
(
    uuid      VARCHAR primary key not null,
    full_name text                 not null
);

create table contact
(
    id          serial primary key not null,
    resume_uuid VARCHAR           not null references resume (uuid) on delete cascade,
    type        text               not null,
    value       text               not null
);
create unique index contact__uuid_type_index on contact (resume_uuid, type);

create table section
(
    id          serial primary key not null,
    resume_uuid VARCHAR           not null references resume (uuid) on delete cascade,
    type        text               not null,
    content     text               not null

);
create unique index section_uuid_type_index on section (resume_uuid, type);


