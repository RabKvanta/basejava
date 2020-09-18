SELECT * FROM resume r
      LEFT  JOIN section c
           ON r.uuid = c.resume_uuid
            ORDER BY full_name,uuid;
        /*WHERE r.uuid = 'uuid2'*/


