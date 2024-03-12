insert into animal_feed (id, brand_name, feed_type, amount_of_kilograms_left)
values (1, 'Fresh Zebra', 2, 30),
(2, 'Mixed vegetables', 0, 12);

insert into caretaker (id, name) values
(1, 'Maarten'),
(2, 'Willem');

insert into species (id, name, kilograms_per_feeding_moment, diet)
values (1, 'Tiger', 20, 1);

insert into species_feed (species_id, feed_id) values (1,1);