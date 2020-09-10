create table resume
(
    uuid      char(36) primary key not null,
    full_name text                 not null
);

create table contact
(
    id          serial primary key not null,
    resume_uuid char(36)           not null references resume (uuid) on delete cascade,
    type        text               not null,
    value       text               not null
);
create unique index contact__uuid_type_index
    on contact (resume_uuid, type);


