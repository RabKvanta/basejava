SELECT * FROM resume r
      LEFT  JOIN contact c
           ON r.uuid = c.resume_uuid
      LEFT  JOIN section s
                 ON r.uuid = s.resume_uuid
            ORDER BY full_name,uuid;
/* SELECT * FROM resume   WHERE r.uuid = 'uuid2'*/


