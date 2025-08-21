CREATE TABLE dungeon_solutions (
  id UUID PRIMARY KEY,
  user_id TEXT NOT NULL,
  grid JSONB NOT NULL,
  result INTEGER NOT NULL,
  variant TEXT NOT NULL,
  duration_ms BIGINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE experiment_exposures (
  request_id UUID PRIMARY KEY,
  experiment_key TEXT NOT NULL,
  variant TEXT NOT NULL,
  user_id TEXT NOT NULL,
  assigned_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX ON dungeon_solutions (variant);
CREATE INDEX ON dungeon_solutions (created_at);
CREATE INDEX ON experiment_exposures (experiment_key, variant);
