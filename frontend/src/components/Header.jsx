import './Header.css'

function Header({ title = 'Panel principal', userName, role }) {
  return (
    <header className="medisync-header">
      <h1 className="medisync-header__title">
        {title}
      </h1>

      {(userName || role) && (
        <div className="medisync-header__user">
          {userName && (
            <span className="medisync-header__name">
              {userName}
            </span>
          )}

          {role && (
            <span className="medisync-header__role">
              {role}
            </span>
          )}
        </div>
      )}
    </header>
  )
}

export default Header