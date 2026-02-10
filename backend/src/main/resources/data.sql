INSERT INTO aeronaves (nome, marca, ano, descricao, vendido, created, updated)
SELECT * FROM (VALUES
  ('E2-190', 'Embraer', 2014, 'Jato regional de última geração com eficiência superior', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('737-100', 'Boeing', 1998, 'Aeronave comercial de médio porte', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('KC-390', 'Embraer', 2015, 'Avião de transporte militar multimissão', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('A320', 'Airbus', 1995, 'Avião de passageiros de corredor único', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('787 Dreamliner', 'Boeing', 2011, 'Aeronave widebody de longo alcance', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('A350', 'Airbus', 2018, 'Avião de fuselagem larga com tecnologia avançada', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Phenom 300', 'Embraer', 2020, 'Jato executivo leve mais vendido do mundo', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Citation X', 'Cessna', 2012, 'Jato executivo de alta velocidade', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Global 7500', 'Bombardier', 2019, 'Jato executivo de ultra longo alcance', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('A380', 'Airbus', 2007, 'O maior avião de passageiros do mundo', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
) AS v(nome, marca, ano, descricao, vendido, created, updated)
WHERE NOT EXISTS (SELECT 1 FROM aeronaves LIMIT 1);
