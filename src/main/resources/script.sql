SELECT * FROM information_schema.tables WHERE table_schema = 'public';



SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'car';

select j.grade, j.visit, e.id, e.name, s.id, s.name, l.id, l.theme from journal j
    inner join employee e on j.employee_id = e.id
    inner join student s on j.student_id=s.id
    inner join lesson l on j.lesson_id = l.id
order by s.id;

delete from student where id = 15;