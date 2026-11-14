import './Input.css'

function Input({
  id,
  label,
  type = 'text',
  name,
  value,
  onChange,
  placeholder,
  required = false,
  disabled = false,
  error,
}) {
  return (
    <div className="medisync-input-group">
      <label htmlFor={id} className="medisync-input-label">
        {label}
      </label>

      <input
        id={id}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
        disabled={disabled}
        aria-invalid={Boolean(error)}
        aria-describedby={error ? `${id}-error` : undefined}
        className={`medisync-input ${error ? 'medisync-input--error' : ''}`}
      />

      {error && (
        <span id={`${id}-error`} className="medisync-input-error" role="alert">
          {error}
        </span>
      )}
    </div>
  )
}

export default Input