import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

const styles = {
  wrapper: {
    position: 'relative',
    overflow: 'hidden',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    transform: 'translateX(50%)',
    borderRadius: '3px 3px 0 0',
  },
  adContainer: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    backgroundColor: '#ffffff',
    padding: '16px',
    borderRadius: '8px',
    boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)',
    width: '100%',
    maxWidth: 'calc(100% - 32px)',
    boxSizing: 'border-box'
  },
  header: {
    display: 'flex',
    alignItems: 'center',
    gap: '8px',
    marginBottom: '6px'
  },
  adLabel: {
    fontSize: '12px',
    color: '#707070',
    display: 'flex',
    alignItems: 'center',
    gap: '8px'
  },
  bullet: {
    color: '#707070'
  },
  url: {
    fontSize: '12px',
    color: '#707070'
  },
  title: {
    color: '#47b375',
    fontSize: '20px',
    fontWeight: 'normal',
    margin: '0 0 4px 0'
  },
  description: {
    color: '#707070',
    fontSize: '14px',
    fontWeight: 'normal',
    margin: 0,
    lineHeight: '1.4'
  },
  closeButton: {
    position: 'absolute',
    top: '8px',
    right: '8px',
    width: '20px',
    height: '20px',
    border: 'none',
    background: 'transparent',
    cursor: 'pointer',
    padding: 0,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#fff',
    fontSize: '18px',
    lineHeight: 1,
    opacity: 0.7,
    transition: 'opacity 0.2s ease'
  }
};

const MockGoogleAd = ({
  adType = 'display',
  width: propWidth,
  height: propHeight,
  backgroundColor = '#ffffff',
  refreshInterval = 30000,
  onClose
}) => {
  const width = propWidth ?? (adType === 'banner' ? 728 : 300);
  const height = propHeight ?? (adType === 'banner' ? 90 : 250);
  const [isVisible, setIsVisible] = useState(true);

  const [adContent, setAdContent] = useState({
    title: 'Modern Design & Co',
    description: 'The best modern furniture essentials for your home.',
    url: 'example-business.com'
  });

  useEffect(() => {
    const interval = setInterval(() => {
      const mockAds = [
        {
          title: 'Modern Design & Co',
          description: 'The best modern furniture essentials for your home.',
          url: 'example-business.com'
        },
        {
          title: 'Interior Classics & Co',
          description: 'Premium furniture for modern living spaces.',
          url: 'example-interiors.com'
        },
        {
          title: 'Home & Design Studio',
          description: 'Curated collection of contemporary furniture.',
          url: 'example-studio.com'
        }
      ];

      const randomAd = mockAds[Math.floor(Math.random() * mockAds.length)];
      setAdContent(randomAd);
    }, refreshInterval);

    return () => clearInterval(interval);
  }, [refreshInterval]);

  const handleClose = () => {
    setIsVisible(false);
    if (onClose) {
      onClose();
    }
  };

  if (!isVisible) {
    return null;
  }

  return (
    <div 
      style={{
        ...styles.wrapper,
        width: `${width}px`,
        height: `${height}px`,
        backgroundColor
      }}
    >
        <button 
          style={styles.closeButton}
          onClick={handleClose}
          aria-label="Close advertisement"
          onMouseOver={(e) => e.currentTarget.style.opacity = '1'}
          onMouseOut={(e) => e.currentTarget.style.opacity = '0.7'}
        >
          ×
        </button>
      <div style={styles.adContainer}>
        <div style={styles.header}>
          <div style={styles.adLabel}>
            <span>Ad</span>
            <span style={styles.bullet}>•</span>
            <span style={styles.url}>{adContent.url}</span>
          </div>
        </div>
        <h3 style={styles.title}>{adContent.title}</h3>
        <p style={styles.description}>{adContent.description}</p>
      </div>
    </div>
  );
};

MockGoogleAd.propTypes = {
    adType: PropTypes.oneOf(['display', 'banner']),
    width: PropTypes.number,
    height: PropTypes.number,
    backgroundColor: PropTypes.string,
    refreshInterval: PropTypes.number,
    onClose: PropTypes.func
};

export default MockGoogleAd;