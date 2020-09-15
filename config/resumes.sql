SELECT * FROM resume r
      LEFT  JOIN contact c
           ON r.uuid = c.resume_uuid;
        /*WHERE r.uuid = 'uuid2'*/